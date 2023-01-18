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
    public List<GroupsFindRes> findList(String userId) {
        List<Groups> groupsList = groupsRepository.findByMemberUsers(findUser(userId))
            .orElse(List.of());

        return new GroupsFindRes().ofList(groupsList);
    }

    @Transactional
    public void add(String userId, String name) {
        groupsRepository.save(new Groups(findUser(userId), name));
    }

    @Transactional
    public void targetUserInvite(GroupInviteReq req, String userId) {
        User sendUser = findUser(userId);
        User targetUser = findTargetUserEmail(req.getEmail());
        Groups groups = findGroups(req.getGroupsId());

        checkGroupsInMember(sendUser, groups);
        checkInvited(targetUser, groups);

        invitedGroupsRepository.save(new InvitedGroups(targetUser, sendUser, groups));
    }


    @Transactional
    public List<InviteGroupsUserRes> findInvite(String userId) {
        List<InvitedGroups> invitedGroups = invitedGroupsRepository.findByTargetUser(findUser(userId))
            .orElse(List.of());

        return invitedGroups.stream().map(InviteGroupsUserRes::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void acceptInvite(UserGroupsInviteReq req, String userId) {
        InvitedGroups invitedGroups = findInvitedGroups(req.getInviteId());

        groupsRepository.findById(invitedGroups.getGroupsId())
            .orElseThrow(() -> new ApiException(ErrorCode.NO_TEAM_INVITES))
            .updateMember(findUser(userId));

        invitedGroupsRepository.delete(invitedGroups);
    }

    @Transactional
    public void cancelInvite(UserGroupsInviteReq req, String userId) {
        InvitedGroups invited = findInvitedGroups(req.getInviteId());
        checkInvitedTarget(findUser(userId), invited);
        invitedGroupsRepository.delete(invited);
    }

    private void checkInvitedTarget(User targetUser, InvitedGroups invited) {
        if (!invited.targetUserEquals(targetUser)) {
            throw new ApiException(ErrorCode.NO_GROUP_INVITES);
        }
    }

    private void checkInvited(User targetUser, Groups groups) {
        if (invitedGroupsRepository.findByTargetUserAndGroups(targetUser, groups).isPresent()) {
            throw new ApiException(ErrorCode.OVERLAP_INVITED_GROUPS);
        }
    }

    private User findTargetUserEmail(String email) {
        return userRepository.findByInformationEmail(email)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TARGET_USER));
    }

    private void checkGroupsInMember(User user, Groups groups) {
        if (!groups.isMembers(user)) {
            throw new ApiException(ErrorCode.YOUR_NOT_GROUP);
        }
    }

    private InvitedGroups findInvitedGroups(Long inviteId) {
        return invitedGroupsRepository.findById(inviteId)
            .orElseThrow(() -> new ApiException(ErrorCode.NO_GROUP_INVITES));
    }

    private Groups findGroups(Long groupsId) {
        return groupsRepository.findById(groupsId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));
    }

    private User findUser(String userId) {
        return userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
