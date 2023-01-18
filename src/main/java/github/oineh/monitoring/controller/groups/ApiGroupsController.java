package github.oineh.monitoring.controller.groups;

import github.oineh.monitoring.controller.groups.group.req.GroupInviteReq;
import github.oineh.monitoring.controller.groups.req.GroupsCreateReq;
import github.oineh.monitoring.controller.groups.res.GroupsFindRes;
import github.oineh.monitoring.domain.groups.GroupsService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ApiGroupsController {

    private final GroupsService groupsService;

    @GetMapping
    public List<GroupsFindRes> list(Principal principal) {
        return groupsService.findList(principal.getName());
    }

    @PostMapping
    public void createGroups(@RequestBody GroupsCreateReq req, Principal principal) {
        groupsService.add(principal.getName(), req.getName());
    }

    @PostMapping("/invite")
    public void invite(@RequestBody GroupInviteReq req, Principal principal) {
        groupsService.targetUserInvite(req, principal.getName());
    }


}
