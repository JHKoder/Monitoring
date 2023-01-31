package github.oineh.monitoring.department.web;

import github.oineh.monitoring.department.service.DepartmentService;
import github.oineh.monitoring.department.web.req.AddDepartmentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/dept")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;


    @PostMapping
    public void createGroupDept(@RequestBody AddDepartmentRequest req, Principal principal) {
        departmentService.createDepartment(req, principal.getName());
    }
}
