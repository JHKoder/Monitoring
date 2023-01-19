package github.oineh.monitoring.groups.group.dept.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.domain.Dept;
import github.oineh.monitoring.groups.group.dept.domain.DeptRepository;
import github.oineh.monitoring.groups.group.dept.web.req.DeptAddReq;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DeptService {

    private final DeptRepository deptRepository;
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;


    @Transactional(readOnly = true)
    public void createGroup(DeptAddReq req, String userId) {
        groupsRepository.findById(req.getGroupsId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS))
                .updateDept(deptSave(findUser(userId), req.getName()));
    }

    private Dept deptSave(User user, String name) {
        return deptRepository.save(new Dept(user, name));
    }

    private User findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
