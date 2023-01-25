package github.oineh.monitoring.groups.group.web;

import github.oineh.monitoring.groups.service.GroupsService;
import github.oineh.monitoring.groups.web.req.GroupsCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class ApiGroupController {

    private final GroupsService groupsService;

    @PostMapping
    public void createGroups(@RequestBody GroupsCreateReq req, Principal principal) {
        groupsService.createGroup(principal.getName(), req.getName());
    }
}
