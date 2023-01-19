package github.oineh.monitoring.groups.invit.web;

import github.oineh.monitoring.groups.invit.service.InviteGroupService;
import github.oineh.monitoring.groups.invit.web.req.GroupInviteSendReq;
import github.oineh.monitoring.groups.invit.web.req.GroupInviteReq;
import github.oineh.monitoring.groups.invit.web.res.InviteGroupsUserRes;

import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/group/invite")
@RequiredArgsConstructor
public class ApiInviteGroupsController {

    private final InviteGroupService inviteGroupService;


    @GetMapping
    public List<InviteGroupsUserRes> findGroupsInvite(Principal principal) {
        return inviteGroupService.findInvite(principal.getName());
    }

    @PostMapping
    public void invite(@RequestBody GroupInviteSendReq req, Principal principal) {
        inviteGroupService.targetUserInvite(req, principal.getName());
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
