package github.oineh.monitoring.controller.group;

import github.oineh.monitoring.controller.group.req.GroupCreateTeamDeptReq;
import github.oineh.monitoring.controller.group.req.GroupCreateTeamReq;
import github.oineh.monitoring.controller.group.res.GroupListRes;
import github.oineh.monitoring.domain.groups.group.GroupService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/room/{groupId}")
    public ResponseEntity<GroupListRes> groupInRoom(@PathVariable("groupId") Long groupId, Principal principal) {
        GroupListRes res = groupService.findGroup(groupId, principal.getName());

        return ResponseEntity.ok(res);
    }


    @PostMapping("/add/dept")
    public ResponseEntity<Void> createGroupDept(@RequestBody GroupCreateTeamDeptReq req, Principal principal) {
        groupService.createGroup(req, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/team")
    public ResponseEntity<Void> createGroupTeam(@RequestBody GroupCreateTeamReq req, Principal principal) {
        groupService.createGroup(req, principal.getName());

        return ResponseEntity.ok().build();
    }


}
