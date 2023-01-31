package github.oineh.monitoring.department.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.department.domain.Department;
import github.oineh.monitoring.department.domain.DepartmentRepository;
import github.oineh.monitoring.department.web.req.AddDepartmentRequest;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;

    @Transactional
    public void createDepartment(AddDepartmentRequest req, String userId) {
        Groups groups = groupsRepository.findById(req.getGroupsId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        Department department = new Department(findUser(userId), req.getName());

        departmentRepository.save(department);
        groups.updateDept(department);
    }

    private User findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
