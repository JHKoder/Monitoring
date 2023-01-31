package github.oineh.monitoring.groups.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.web.rest.res.GroupInformationResponse;
import github.oineh.monitoring.groups.web.rest.res.GroupResponse;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupsService {

    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;


    @Transactional(readOnly = true)
    public List<GroupResponse> findList(String userId) {
        return GroupResponse.ofList(
                groupsRepository.findByMemberUsers(findUser(userId))
                        .orElse(List.of())
        );
    }

    @Transactional
    public void createGroup(String userId, String name) {
        User user = findUser(userId);
        groupsRepository.save(new Groups(user, name));
    }

    public void validateGroupInMember(Long groupId, String email) {
        validateUserInGroup(findGroups(groupId), findUserEmail(email));
    }

    @Transactional(readOnly = true)
    public GroupInformationResponse findGroup(Long groupId, String userId) {
        return groupsRepository.findById(groupId)
                .filter(groups -> validateGroupsInMember(groups, findUser(userId)))
                .map(group -> GroupInformationResponse.of(group.getId(), group.getDepts()))
                .orElse(new GroupInformationResponse());
    }

    private boolean validateGroupsInMember(Groups group, User user) {
        if (!group.containsUser(user)) {
            throw new ApiException(ErrorCode.YOUR_NOT_GROUP);
        }
        return true;
    }

    private User findUser(String userId) {
        return userRepository.findByLoginId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private User findUserEmail(String email) {
        return userRepository.findByInformationEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TARGET_EMAIL_USER));
    }

    private Groups findGroups(Long groupId) {
        return groupsRepository.findById(groupId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));
    }

    private void validateUserInGroup(Groups group, User user) {
        if (!group.containsUser(user)) {
            throw new ApiException(ErrorCode.YOUR_NOT_GROUP);
        }
    }
}
