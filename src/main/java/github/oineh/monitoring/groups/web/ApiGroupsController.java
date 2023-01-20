package github.oineh.monitoring.groups.web;

import github.oineh.monitoring.groups.group.service.GroupService;
import github.oineh.monitoring.groups.service.GroupsService;
import github.oineh.monitoring.groups.web.req.GroupsCreateReq;
import github.oineh.monitoring.groups.web.res.GrouopsRes;
import github.oineh.monitoring.groups.web.res.GroupListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return groupService.findGroupIn(groupId, principal.getName());
    }

    @GetMapping
    public List<GrouopsRes> findGroupsList(Principal principal) {
        return groupsService.findList(principal.getName());
    }

    @PostMapping
    public void createGroups(@RequestBody GroupsCreateReq req, Principal principal) {
        groupsService.add(principal.getName(), req.getName());
    }
}
