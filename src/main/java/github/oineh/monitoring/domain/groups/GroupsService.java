package github.oineh.monitoring.domain.groups;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.controller.groups.group.req.GroupInviteReq;
import github.oineh.monitoring.controller.groups.res.GroupsFindRes;
import github.oineh.monitoring.controller.user.req.UserGroupsInviteReq;
import github.oineh.monitoring.controller.user.res.InviteGroupsUserRes;
import github.oineh.monitoring.domain.groups.invit.InvitedGroups;
import github.oineh.monitoring.domain.groups.invit.InvitedGroupsRepository;
import github.oineh.monitoring.domain.user.User;
import github.oineh.monitoring.domain.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupsService {

    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;
    private final InvitedGroupsRepository invitedGroupsRepository;

    @Transactional
    public List<GroupsFindRes> findList(String loginId) {
        User user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        List<Groups> groupsList = groupsRepository.findByMemberUser(user).orElse(null);

        return new GroupsFindRes().ofList(groupsList);
    }

    @Transactional
    public void add(String loginId, String name) {
        User user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Groups groups = new Groups(user, name);

        groupsRepository.save(groups);
    }

    @Transactional
    public void targetUserInvite(GroupInviteReq req, String userId) {
        User sendUser = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        User targetUser = userRepository.findByInformationEmail(req.getEmail())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TARGET_USER));

        Groups groups = groupsRepository.findById(req.getGroupsId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        if (invitedGroupsRepository.findByTargetUserAndGroups(targetUser, groups).isPresent()) {
            throw new ApiException(ErrorCode.OVERLAP_INVITED_GROUPS);
        }

        InvitedGroups invited = new InvitedGroups(targetUser, sendUser, groups);

        invitedGroupsRepository.save(invited);
    }

    //그룹 초대 리스트 보기
    @Transactional
    public List<InviteGroupsUserRes> findInvite(String userId) {
        User user = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        List<InvitedGroups> invitedGroups = invitedGroupsRepository.findByTargetUser(user)
            .orElse(null);

        return invitedGroups.stream().map(InviteGroupsUserRes::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void acceptInvite(UserGroupsInviteReq req, String userId) {
        User user = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        InvitedGroups invitedGroups = invitedGroupsRepository.findById(req.getInviteId())
            .orElseThrow(() -> new ApiException(ErrorCode.NO_GROUP_INVITES));

        Groups groups = groupsRepository.findById(invitedGroups.getGroups().getId())
            .orElseThrow();

        groups.updateMember(user);
        invitedGroupsRepository.delete(invitedGroups);
    }

    @Transactional
    public void cancelInvite(UserGroupsInviteReq req, String userId) {
        userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        InvitedGroups invitedGroups = invitedGroupsRepository.findById(req.getInviteId())
            .orElseThrow(() -> new ApiException(ErrorCode.NO_GROUP_INVITES));

        invitedGroupsRepository.delete(invitedGroups);
    }
}
