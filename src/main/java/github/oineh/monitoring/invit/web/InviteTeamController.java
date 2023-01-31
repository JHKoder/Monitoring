package github.oineh.monitoring.invit.web;

import github.oineh.monitoring.groups.service.GroupsService;
import github.oineh.monitoring.invit.service.InviteTeamService;
import github.oineh.monitoring.invit.web.req.InviteTeamCancelRequest;
import github.oineh.monitoring.invit.web.req.InviteTeamRequest;
import github.oineh.monitoring.invit.web.req.TeamInviteAcceptReq;
import github.oineh.monitoring.invit.web.res.InviteTeamResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/team/invite")
@RequiredArgsConstructor
public class InviteTeamController {

    private final GroupsService groupsService;
    private final InviteTeamService inviteTeamService;

    @GetMapping
    public List<InviteTeamResponse> findInvite(Principal principal) {
        return inviteTeamService.findInvite(principal.getName());
    }

    @PostMapping
    public void makeInvite(@RequestBody InviteTeamRequest req, Principal principal) {
        groupsService.validateGroupInMember(req.getGroupsId(), req.getEmail());
        inviteTeamService.makeInvite(req, principal.getName());
    }

    @PatchMapping
    public void acceptTeamInvite(@RequestBody TeamInviteAcceptReq req, Principal principal) {
        inviteTeamService.acceptInvite(req, principal.getName());
    }

    @DeleteMapping
    public void cancelTeamInvite(@RequestBody InviteTeamCancelRequest req, Principal principal) {
        inviteTeamService.cancelInvite(req, principal.getName());
    }
}
