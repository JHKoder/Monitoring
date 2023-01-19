package github.oineh.monitoring.groups.group.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.web.res.GroupListRes;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;


    @Transactional(readOnly = true)
    public GroupListRes findGroupIn(Long groupId, String userId) {
        return groupsRepository.findById(groupId)
                .filter(groups -> checkGroupsInMember(groups, findUser(userId)))
                .map(group -> GroupListRes.of(group.getId(), group.getDepts()))
                .orElse(new GroupListRes());
    }

    private boolean checkGroupsInMember(Groups group, User user) {
        if (!group.checkMember(user)) {
            throw new ApiException(ErrorCode.YOUR_NOT_GROUP);
        }
        return true;
    }

    private User findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
