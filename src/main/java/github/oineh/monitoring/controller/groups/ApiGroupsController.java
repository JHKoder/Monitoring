package github.oineh.monitoring.controller.groups;

import github.oineh.monitoring.controller.groups.req.GroupsInviteReq;
import github.oineh.monitoring.controller.groups.req.GroupsTakeReq;
import github.oineh.monitoring.controller.groups.res.GroupsFindRes;
import github.oineh.monitoring.controller.groups.res.GroupsInviteListRes;
import github.oineh.monitoring.domain.groups.GroupsService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ApiGroupsController {

    private final GroupsService groupsService;

    @PostMapping("/list")
    public ResponseEntity<List<GroupsFindRes>> list(Principal principal) {
        List<GroupsFindRes> listRes = groupsService.findList(principal.getName());

        return ResponseEntity.ok(listRes);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestParam("name") String name, Principal principal) {
        groupsService.add(principal.getName(), name);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/invite")
    public ResponseEntity<GroupsInviteListRes> viewInvitations(Principal principal) {
        GroupsInviteListRes res = groupsService.getInviteList(principal.getName());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(GroupsInviteReq groupsInviteReq, Principal principal) {
        groupsService.invite(principal.getName(), groupsInviteReq);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/take")
    public ResponseEntity<Void> take(GroupsTakeReq groupsTakeReq, Principal principal) {
        groupsService.targetUserInvited(principal.getName(), groupsTakeReq);

        return ResponseEntity.ok().build();
    }
}
