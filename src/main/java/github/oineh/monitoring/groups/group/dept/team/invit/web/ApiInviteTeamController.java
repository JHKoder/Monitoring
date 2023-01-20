package github.oineh.monitoring.groups.group.dept.team.invit.web;

import github.oineh.monitoring.groups.group.dept.team.invit.service.InviteTeamService;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteAcceptReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteCancelReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.res.InviteTeamUserRes;
import github.oineh.monitoring.groups.service.GroupsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/team/invite")
@RequiredArgsConstructor
public class ApiInviteTeamController {

    private final GroupsService groupsService;
    private final InviteTeamService inviteTeamService;


    @GetMapping
    public List<InviteTeamUserRes> findInvite(Principal principal) {
        return inviteTeamService.findInvite(principal.getName());
    }

    @PostMapping
    public void invite(@RequestBody TeamInviteReq req, Principal principal) {
        groupsService.validateGroupInMember(req.getGroupsId(), req.getEmail());
        inviteTeamService.inviteUser(req, principal.getName());
    }

    @PatchMapping
    public void acceptTeamInvite(@RequestBody TeamInviteAcceptReq req, Principal principal) {
        inviteTeamService.acceptInvite(req, principal.getName());
    }

    @DeleteMapping
    public void cancelTeamInvite(@RequestBody TeamInviteCancelReq req, Principal principal) {
        inviteTeamService.cancelInvite(req, principal.getName());
    }
}
