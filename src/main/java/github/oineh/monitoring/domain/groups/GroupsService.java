package github.oineh.monitoring.domain.groups;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.controller.groups.req.GroupsInviteReq;
import github.oineh.monitoring.controller.groups.req.GroupsTakeReq;
import github.oineh.monitoring.controller.groups.res.GroupsFindRes;
import github.oineh.monitoring.controller.groups.res.GroupsInviteListRes;
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

        Iterable<Long> listId = user.getGroups().stream()
            .map(Groups::getId)
            .collect(Collectors.toList());

        List<Groups> groupsList = groupsRepository.findAllById(listId);

        return new GroupsFindRes().ofList(groupsList);
    }

    public GroupsInviteListRes getInviteList(String loginId) {
        User user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        List<InvitedGroups> invitedGroups = invitedGroupsRepository.findByTargetUser(user.getId())
            .orElse(null);

        return new GroupsInviteListRes(invitedGroups);
    }


    @Transactional
    public void add(String loginId, String name) {
        User user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        System.out.println("그룹 생성 이름" + name);
        Groups groups = new Groups(user, name);
        user.updateGroups(groups);
        groupsRepository.save(groups);
    }

    @Transactional
    public void invite(String userId, GroupsInviteReq groupsInviteReq) {
        User targetUser = userRepository.findByInformationEmail(groupsInviteReq.getTargetEmail())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TARGET_USER));
        User sendUser = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_SEND_USER));
        Groups groups = groupsRepository.findById(groupsInviteReq.getGroupsId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        InvitedGroups invite = new InvitedGroups(targetUser, sendUser, groups);

        invitedGroupsRepository.save(invite);
    }

    @Transactional
    public void targetUserInvited(String userId, GroupsTakeReq groupsTakeReq) {
        User targetUser = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        InvitedGroups invite = invitedGroupsRepository.findByIdAndTargetUser(groupsTakeReq.getInvite(),
                targetUser.getId())
            .orElseThrow(() -> new ApiException(ErrorCode.NO_GROUP_INVITES));

        invitedGroupsRepository.delete(invite);
    }
}
