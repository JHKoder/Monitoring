package github.oineh.monitoring.domain.groups.group;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.controller.group.req.GroupCreateTeamDeptReq;
import github.oineh.monitoring.controller.group.req.GroupCreateTeamReq;
import github.oineh.monitoring.controller.group.res.GroupListRes;
import github.oineh.monitoring.controller.team.req.TeamInviteReq;
import github.oineh.monitoring.controller.user.req.UserGroupsTeamInviteReq;
import github.oineh.monitoring.controller.user.res.InviteTeamUserRes;
import github.oineh.monitoring.domain.groups.Groups;
import github.oineh.monitoring.domain.groups.GroupsRepository;
import github.oineh.monitoring.domain.groups.group.category.Dept;
import github.oineh.monitoring.domain.groups.group.category.DeptRepository;
import github.oineh.monitoring.domain.groups.group.category.Team;
import github.oineh.monitoring.domain.groups.group.category.TeamRepository;
import github.oineh.monitoring.domain.groups.group.invit.InvitedGroup;
import github.oineh.monitoring.domain.groups.group.invit.InvitedGroupRepository;
import github.oineh.monitoring.domain.user.User;
import github.oineh.monitoring.domain.user.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final DeptRepository deptRepository;
    private final TeamRepository teamRepository;

    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;
    private final InvitedGroupRepository invitedGroupRepository;

    // 그룹네 모든 소속 & 팀 리턴
    @Transactional
    public GroupListRes findGroup(Long groupId, String userId) {
        userRepository.findByLoginId(userId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Groups groups = groupsRepository.findById(groupId).orElse(null);

        List<Dept> list = findDeptByGroupId(groups);

        return GroupListRes.of(groupId, list);
    }

    public List<Dept> findDeptByGroupId(Groups groups) {
        List<Long> deptIds = groups.getDept().stream().map(Dept::getId).collect(Collectors.toList());

        return deptRepository.findAllById(deptIds);
    }


    // 부서 생성
    @Transactional
    public void createGroup(GroupCreateTeamDeptReq req, String userId) {
        Groups groups = groupsRepository.findById(req.getGroupsId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        User user = getUserByLoginId(userId);

        Dept dept = deptRepository.save(new Dept(user, req.getName()));
        groups.updateDept(dept);
    }


    //부서내 팀 생성
    @Transactional
    public void createGroup(GroupCreateTeamReq req, String userId) {
        Groups groups = groupsRepository.findById(req.getGroupsId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        User user = getUserByLoginId(userId);

        Dept dept = groups.getDept().stream().filter(dep -> dep.getId().equals(req.getDeptId())).findFirst()
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_GROUPS_DEPT));

        Team team = teamRepository.save(new Team(user, req.getName()));
        dept.updateTeam(team);
    }

    @Transactional
    public List<InviteTeamUserRes> findInvite(String userId) {
        User user = getUserByLoginId(userId);
        List<InvitedGroup> invited = getInvitationsByTargetUser(user);
        return mapInvitationsToResponse(invited);
    }

    private List<InvitedGroup> getInvitationsByTargetUser(User user) {
        return invitedGroupRepository.findByTargetUser(user).orElse(Collections.emptyList());
    }

    private List<InviteTeamUserRes> mapInvitationsToResponse(List<InvitedGroup> invited) {
        return invited.stream().map(InviteTeamUserRes::new).collect(Collectors.toList());
    }

    @Transactional
    public void acceptInvite(UserGroupsTeamInviteReq req, String userId) {
        User user = getUserByLoginId(userId, ErrorCode.NOT_FOUND_SEND_USER);

        Team team = teamRepository.findById(req.getTeamId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));

        InvitedGroup invitedGroup = invitedGroupRepository.findByTargetUserAndTeam(user, team)
            .orElseThrow(() -> new ApiException(ErrorCode.NO_TEAM_INVITES));

        team.updateMember(user);
        invitedGroupRepository.delete(invitedGroup);
    }

    @Transactional
    public void cancelInvite(UserGroupsTeamInviteReq req, String userId) {
        User user = userRepository.findByLoginId(userId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Team team = teamRepository.findById(req.getTeamId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));

        InvitedGroup invitedGroup = invitedGroupRepository.findByTargetUserAndTeam(user, team)
            .orElseThrow(() -> new ApiException(ErrorCode.NO_TEAM_INVITES));

        invitedGroupRepository.delete(invitedGroup);
    }

    @Transactional
    public void targetUserInvite(TeamInviteReq req, String userId) {
        Team team = getTeamById(req.getTeamId());
        User targetUser = getUserByEmail(req.getEmail(), ErrorCode.NOT_FOUND_TARGET_USER);
        User sendUser = getUserByLoginId(userId, ErrorCode.NOT_FOUND_SEND_USER);

        checkSendUserIsTeamMember(sendUser, team);

        if (invitedGroupRepository.findByTargetUserAndTeam(targetUser, team).isPresent()) {
            throw new ApiException(ErrorCode.OVERLAP_INVITED_TEAM);
        }

        InvitedGroup invited = new InvitedGroup(targetUser, sendUser, team);
        invitedGroupRepository.save(invited);
    }


    private User getUserByLoginId(String loginId, ErrorCode code) {
        return userRepository.findByLoginId(loginId).orElseThrow(() -> new ApiException(code));
    }

    private User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private Team getTeamById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));
    }

    private User getUserByEmail(String email, ErrorCode errorCode) {
        return userRepository.findByInformationEmail(email).orElseThrow(() -> new ApiException(errorCode));
    }

    private void checkSendUserIsTeamMember(User sendUser, Team team) {
        boolean isSendUser = team.getCreateUser().getId().equals(sendUser.getId()) || team.getMember().stream()
            .anyMatch(user -> user.getId().equals(sendUser.getId()));

        if (!isSendUser) {
            throw new ApiException(ErrorCode.YOUR_NOT_TEAM);
        }
    }

}
