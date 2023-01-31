package github.oineh.monitoring.invit.web;

import github.oineh.monitoring.invit.service.InviteGroupService;
import github.oineh.monitoring.invit.web.req.GroupInviteReq;
import github.oineh.monitoring.invit.web.req.GroupInviteSendReq;
import github.oineh.monitoring.invit.web.res.InviteGroupsUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/group/invite")
@RequiredArgsConstructor
public class InviteGroupsController {

    private final InviteGroupService inviteGroupService;

    @GetMapping
    public List<InviteGroupsUserRes> findGroupsInvite(Principal principal) {
        return inviteGroupService.findInvite(principal.getName());
    }

    @PostMapping
    public void makeInvite(@RequestBody GroupInviteSendReq req, Principal principal) {
        inviteGroupService.makeInvite(req, principal.getName());
    }

    @PatchMapping
    public void acceptGroupsInvite(@RequestBody GroupInviteReq req, Principal principal) {
        inviteGroupService.acceptInvite(req, principal.getName());
    }

    @DeleteMapping
    public void cancelGroupsInvite(@RequestBody GroupInviteReq req, Principal principal) {
        inviteGroupService.cancelInvite(req, principal.getName());
    }
}
