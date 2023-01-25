package github.oineh.monitoring.groups.web;

import github.oineh.monitoring.groups.group.service.GroupService;
import github.oineh.monitoring.groups.service.GroupsService;
import github.oineh.monitoring.groups.web.res.GrouopsRes;
import github.oineh.monitoring.groups.web.res.GroupListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ApiGroupsController {

    private final GroupsService groupsService;
    private final GroupService groupService;


    @GetMapping("/{groupId}")
    public GroupListRes groupInRoom(@PathVariable("groupId") Long groupId, Principal principal) {
        return groupService.findAllDepartmentsAndTeamMembers(groupId, principal.getName());
    }

    @GetMapping
    public List<GrouopsRes> findGroupsList(Principal principal) {
        return groupsService.findList(principal.getName());
    }
}
