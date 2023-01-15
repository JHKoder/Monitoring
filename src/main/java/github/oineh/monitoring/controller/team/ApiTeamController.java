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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class ApiTeamController {

    private final ConnectService connectService;
    private final GroupService groupService;


    @PostMapping("/find/domain/{teamId}")
    public ResponseEntity<List<TeamInDomainRes>> domainList(@PathVariable("teamId") Long teamId) {
        List<TeamInDomainRes> res = connectService.findTeamInDomain(teamId);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/find/member/{teamId}")
    public ResponseEntity<List<TeamInMemberRes>> memberList(@PathVariable("teamId") Long teamId) {
        List<TeamInMemberRes> res = connectService.findTeamInMember(teamId);

        return ResponseEntity.ok(res);
    }


    @PostMapping("/ping/domain/{teamId}/{connectId}")
    public ResponseEntity<TeamInDomainPingRes> findTeamDomain(@PathVariable("teamId") Long teamId,
        @PathVariable Long connectId) {
        TeamInDomainPingRes res = connectService.findTeamInConnectDomainList(teamId, connectId);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/ping/member/{teamId}/{connectId}")
    public ResponseEntity<TeamInMemberPingRes> findTeam(@PathVariable("teamId") Long teamId,
        @PathVariable Long connectId) {
        TeamInMemberPingRes res = connectService.findTeamInConnectMemberList(teamId, connectId);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(@RequestBody TeamInviteReq req, Principal principal) {
        groupService.targetUserInvite(req, principal.getName());

        return ResponseEntity.ok().build();
    }


    @PostMapping("/add/url")
    public ResponseEntity<Void> createUrl(@RequestBody TeamCreateUrlReq req) {
        connectService.createUrl(req);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/ip/port")
    public ResponseEntity<Void> createPort(@RequestBody TeamCreatePortReq req) {
        connectService.createIpPort(req);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/ip")
    public ResponseEntity<Void> createIp(@RequestBody TeamCreateIpReq req) {
        connectService.createIp(req);

        return ResponseEntity.ok().build();

    }
}
