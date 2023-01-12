package github.oineh.monitoring.controller.user;

import github.oineh.monitoring.controller.user.req.UserGroupsInviteReq;
import github.oineh.monitoring.controller.user.req.UserGroupsTeamInviteReq;
import github.oineh.monitoring.controller.user.res.InviteGroupsUserRes;
import github.oineh.monitoring.controller.user.res.InviteTeamUserRes;
import github.oineh.monitoring.domain.groups.GroupsService;
import github.oineh.monitoring.domain.groups.group.GroupService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiUserController {

    private final GroupsService groupsService;
    private final GroupService groupService;

    @GetMapping("/groups/invite")
    public ResponseEntity<List<InviteGroupsUserRes>> findGroupsInvite(Principal principal) {
        List<InviteGroupsUserRes> list = groupsService.findInvite(principal.getName());

        return ResponseEntity.ok(list);
    }

    @PostMapping("/groups/invite/accept")
    public ResponseEntity<Void> acceptGroupsInvite(@RequestBody UserGroupsInviteReq req, Principal principal) {
        groupsService.acceptInvite(req, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/groups/invite/cancel")
    public ResponseEntity<Void> noGroupsInvite(@RequestBody UserGroupsInviteReq req, Principal principal) {
        groupsService.cancelInvite(req, principal.getName());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/team/invite")
    public ResponseEntity<List<InviteTeamUserRes>> findTeamInvite(Principal principal) {
        List<InviteTeamUserRes> list = groupService.findInvite(principal.getName());

        return ResponseEntity.ok(list);
    }

    @PostMapping("/team/invite/accept")
    public ResponseEntity<Void> acceptTeamInvite(@RequestBody UserGroupsTeamInviteReq req, Principal principal) {
        groupService.acceptInvite(req, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/team/invite/cancel")
    public ResponseEntity<Void> noTeamInvite(@RequestBody UserGroupsTeamInviteReq req, Principal principal) {
        groupService.cancelInvite(req, principal.getName());

        return ResponseEntity.ok().build();
    }

}
