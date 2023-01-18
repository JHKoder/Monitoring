package github.oineh.monitoring.controller.team;


import github.oineh.monitoring.controller.team.req.TeamCreateIpReq;
import github.oineh.monitoring.controller.team.req.TeamCreatePortReq;
import github.oineh.monitoring.controller.team.req.TeamCreateUrlReq;
import github.oineh.monitoring.controller.team.req.TeamInviteReq;
import github.oineh.monitoring.controller.team.res.TeamInDomainPingRes;
import github.oineh.monitoring.controller.team.res.TeamInDomainRes;
import github.oineh.monitoring.controller.team.res.TeamInMemberPingRes;
import github.oineh.monitoring.controller.team.res.TeamInMemberRes;
import github.oineh.monitoring.domain.connect.ConnectService;
import github.oineh.monitoring.domain.groups.group.GroupService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class ApiTeamController {

    private final ConnectService connectService;
    private final GroupService groupService;

    @GetMapping("/{teamId}/domain")
    public List<TeamInDomainRes> domainList(@PathVariable("teamId") Long teamId) {
        return connectService.findTeamInDomain(teamId);
    }

    @GetMapping("/{teamId}/member")
    public List<TeamInMemberRes> memberList(@PathVariable("teamId") Long teamId) {
        return connectService.findTeamInMember(teamId);
    }

    @GetMapping("/{teamId}/connect/{connectId}")
    public TeamInDomainPingRes findTeamDomain(@PathVariable("teamId") Long teamId,
        @PathVariable Long connectId) {
        return connectService.findTeamInConnectDomain(teamId, connectId);
    }

    @GetMapping("/{teamId}/member/connect/{connectId}")
    public TeamInMemberPingRes findTeam(@PathVariable("teamId") Long teamId,
        @PathVariable Long connectId) {
        return connectService.findTeamInConnectMemberList(teamId, connectId);
    }

    @PostMapping("/invite")
    public void invite(@RequestBody TeamInviteReq req, Principal principal) {
        groupService.targetUserInvite(req, principal.getName());
    }

    @PostMapping("/url")
    public void createUrl(@RequestBody TeamCreateUrlReq req) {
        connectService.createUrl(req);
    }

    @PostMapping("/ip-port")
    public void createPort(@RequestBody TeamCreatePortReq req) {
        connectService.createIpPort(req);
    }

    @PostMapping("/ip")
    public void createIp(@RequestBody TeamCreateIpReq req) {
        connectService.createIp(req);
    }
}
