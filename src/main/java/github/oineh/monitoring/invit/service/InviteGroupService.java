package github.oineh.monitoring.invit.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.invit.domain.InvitedGroups;
import github.oineh.monitoring.invit.domain.InvitedGroupsRepository;
import github.oineh.monitoring.invit.web.req.InviteGroupRequest;
import github.oineh.monitoring.invit.web.req.InviteGroupSendRequest;
import github.oineh.monitoring.invit.web.res.InviteGroupResponse;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InviteGroupService {

    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;
    private final InvitedGroupsRepository invitedGroupsRepository;

    @Transactional
    public void makeInvite(InviteGroupSendRequest req, String userId) {
        User sendUser = findUser(userId);
        User targetUser = findTargetUserEmail(req.getEmail());
        Groups groups = findGroups(req.getGroupsId());

        validateGroupInMember(sendUser, groups);
        validateInvited(targetUser, groups);

        invitedGroupsRepository.save(new InvitedGroups(targetUser, sendUser, groups));
    }

    @Transactional(readOnly = true)
    public List<InviteGroupResponse> findInvite(String userId) {
        List<InvitedGroups> invitedGroups = invitedGroupsRepository.findByTargetUser(findUser(userId))
                .orElse(List.of());

        return invitedGroups.stream().map(InviteGroupResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void acceptInvite(InviteGroupRequest req, String userId) {
        InvitedGroups invitedGroups = findInvitedGroups(req.getInviteId());

        groupsRepository.findById(invitedGroups.getGroupsId())
                .orElseThrow(() -> new ApiException(ErrorCode.NO_TEAM_INVITES))
                .updateMember(findUser(userId));

        invitedGroupsRepository.delete(invitedGroups);
    }

    @Transactional
    public void cancelInvite(InviteGroupRequest req, String userId) {
        InvitedGroups invited = findInvitedGroups(req.getInviteId());

        validateInvitedTarget(findUser(userId), invited);

        invitedGroupsRepository.delete(invited);
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

    private User findTargetUserEmail(String email) {
        return userRepository.findByInformationEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TARGET_USER));
    }

    private void validateInvitedTarget(User targetUser, InvitedGroups invited) {
        if (!invited.targetUserEquals(targetUser)) {
            throw new ApiException(ErrorCode.NO_GROUP_INVITES);
        }
    }

    private void validateInvited(User targetUser, Groups groups) {
        if (invitedGroupsRepository.findByTargetUserAndGroups(targetUser, groups).isPresent()) {
            throw new ApiException(ErrorCode.OVERLAP_INVITED_GROUPS);
        }
    }

    private void validateGroupInMember(User user, Groups groups) {
        if (!groups.containsUser(user)) {
            throw new ApiException(ErrorCode.YOUR_NOT_GROUP);
        }
    }
}
