package github.oineh.monitoring.groups.web;

import github.oineh.monitoring.groups.group.service.GroupService;
import github.oineh.monitoring.groups.invit.web.req.GroupsCreateReq;
import github.oineh.monitoring.groups.service.GroupsService;
import github.oineh.monitoring.groups.web.res.GroupListRes;
import github.oineh.monitoring.groups.web.res.GroupsFindRes;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<GroupsFindRes> list(Principal principal) {
        return groupsService.findList(principal.getName());
    }

    @PostMapping
    public void createGroups(@RequestBody GroupsCreateReq req, Principal principal) {
        groupsService.add(principal.getName(), req.getName());
    }
}
