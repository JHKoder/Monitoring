package github.oineh.monitoring.groups.group.dept.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.domain.Groups;
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


    @Transactional
    public void makeDept(DeptAddReq req, String userId) {
        Groups groups = groupsRepository.findById(req.getGroupsId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        Dept dept = new Dept(findUser(userId), req.getName());

        deptRepository.save(dept);
        groups.updateDept(dept);
    }

    private User findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
