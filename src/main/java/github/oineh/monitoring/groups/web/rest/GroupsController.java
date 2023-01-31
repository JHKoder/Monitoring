package github.oineh.monitoring.groups.web.rest;

import github.oineh.monitoring.groups.service.GroupsService;
import github.oineh.monitoring.groups.web.rest.req.GroupsCreateReq;
import github.oineh.monitoring.groups.web.rest.res.GroupInformationResponse;
import github.oineh.monitoring.groups.web.rest.res.GroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupsController {

    private final GroupsService groupsService;

    @PostMapping
    public void createGroups(@RequestBody GroupsCreateReq req, Principal principal) {
        groupsService.createGroup(principal.getName(), req.getName());
    }

    @GetMapping
    public List<GroupResponse> findGroupsList(Principal principal) {
        return groupsService.findList(principal.getName());
    }

    @GetMapping("/{groupId}")
    public GroupInformationResponse groupInRoom(@PathVariable("groupId") Long groupId, Principal principal) {
        return groupsService.findGroup(groupId, principal.getName());
    }

}
