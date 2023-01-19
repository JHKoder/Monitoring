package github.oineh.monitoring.groups.service;

import static github.oineh.monitoring.config.exception.ErrorCode.NOT_FOUND_TARGET_USER;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.web.res.GrouopsRes;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupsService {

    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;


    @Transactional(readOnly = true)
    public List<GrouopsRes> findList(String userId) {
        List<Groups> groupsList = groupsRepository.findByMemberUsers(findUser(userId))
                .orElse(List.of());

        return new GrouopsRes().ofList(groupsList);
    }

    @Transactional
    public void add(String userId, String name) {
        groupsRepository.save(new Groups(findUser(userId), name));
    }

    public void validateGroupInMember(Long groupId, String email) {
        validateUserGroup(findGroups(groupId), findUserEmail(email));
    }

    private User findUser(String userId) {
        return userRepository.findByLoginId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private User findUserEmail(String email) {
        return userRepository.findByInformationEmail(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_TARGET_USER));
    }

    private Groups findGroups(Long groupId) {
        return groupsRepository.findById(groupId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));
    }

    private void validateUserGroup(Groups group, User user) {
        if (!group.checkMember(user)) {
            throw new ApiException(ErrorCode.YOUR_NOT_GROUP);
        }
    }
}
