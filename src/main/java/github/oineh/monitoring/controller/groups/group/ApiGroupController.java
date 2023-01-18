package github.oineh.monitoring.controller.groups.group;

import github.oineh.monitoring.controller.groups.group.req.GroupCreateTeamDeptReq;
import github.oineh.monitoring.controller.groups.group.req.GroupCreateTeamReq;
import github.oineh.monitoring.controller.groups.group.res.GroupListRes;
import github.oineh.monitoring.domain.groups.group.GroupService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class ApiGroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public GroupListRes groupInRoom(@PathVariable("groupId") Long groupId, Principal principal) {
        return groupService.findGroupIn(groupId, principal.getName());
    }


    @PostMapping("/dept")
    public void createGroupDept(@RequestBody GroupCreateTeamDeptReq req, Principal principal) {
        groupService.createGroup(req, principal.getName());
    }

    @PostMapping("/team")
    public void createGroupTeam(@RequestBody GroupCreateTeamReq req, Principal principal) {
        groupService.createGroup(req, principal.getName());
    }


}
