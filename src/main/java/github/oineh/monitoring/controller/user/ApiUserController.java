package github.oineh.monitoring.controller.user;

import github.oineh.monitoring.controller.user.req.SingUpReq;
import github.oineh.monitoring.controller.user.req.UserGroupsInviteReq;
import github.oineh.monitoring.controller.user.req.UserGroupsTeamInviteReq;
import github.oineh.monitoring.controller.user.res.InviteGroupsUserRes;
import github.oineh.monitoring.controller.user.res.InviteTeamUserRes;
import github.oineh.monitoring.domain.groups.GroupsService;
import github.oineh.monitoring.domain.groups.group.GroupService;
import github.oineh.monitoring.domain.user.UserService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    private final UserService userService;

    @GetMapping("/groups/invite")
    public List<InviteGroupsUserRes> findGroupsInvite(Principal principal) {
        return groupsService.findInvite(principal.getName());
    }

    @PostMapping("/groups/invite")
    public void acceptGroupsInvite(@RequestBody UserGroupsInviteReq req, Principal principal) {
        groupsService.acceptInvite(req, principal.getName());

    }

    @DeleteMapping("/groups/invite")
    public void cancelGroupsInvite(@RequestBody UserGroupsInviteReq req, Principal principal) {
        groupsService.cancelInvite(req, principal.getName());
    }

    @GetMapping("/team/invite")
    public List<InviteTeamUserRes> findTeamInvite(Principal principal) {
        return groupService.findInvite(principal.getName());
    }

    @PostMapping("/team/invite")
    public void acceptTeamInvite(@RequestBody UserGroupsTeamInviteReq req, Principal principal) {
        groupService.acceptInvite(req, principal.getName());
    }

    @DeleteMapping("/team/invite")
    public void cancelTeamInvite(@RequestBody UserGroupsTeamInviteReq req, Principal principal) {
        groupService.cancelInvite(req, principal.getName());
    }

    @PostMapping("/singup")
    public void postSingUp(@RequestBody SingUpReq singUpReq) {
        userService.singup(singUpReq);
    }

}
