package github.oineh.monitoring.groups.group.dept.web;

import github.oineh.monitoring.groups.group.dept.service.DeptService;
import github.oineh.monitoring.groups.group.dept.web.req.DeptAddReq;

import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dept")
@RequiredArgsConstructor
public class ApiDeptController {

    private final DeptService deptService;


    @PostMapping
    public void createGroupDept(@RequestBody DeptAddReq req, Principal principal) {
        deptService.createGroup(req, principal.getName());
    }
}
