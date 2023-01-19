package github.oineh.monitoring.groups.group.dept.team.invit.web;

import github.oineh.monitoring.groups.group.dept.team.invit.service.InviteTeamService;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteAcceptReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteCancelReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.res.InviteTeamUserRes;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteReq;
import github.oineh.monitoring.groups.service.GroupsService;

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
        groupsService.validateGroupInMember(req.getGroupsId(), principal.getName());
        inviteTeamService.inviteUser(req, principal.getName());
    }

    @PatchMapping
    public void acceptTeamInvite(@RequestBody TeamInviteAcceptReq req, Principal principal) {
        groupsService.validateGroupInMember(req.getGroupsId(), principal.getName());
        inviteTeamService.acceptInvite(req, principal.getName());
    }

    @DeleteMapping
    public void cancelTeamInvite(@RequestBody TeamInviteCancelReq req, Principal principal) {
        inviteTeamService.cancelInvite(req, principal.getName());
    }
}
