package github.oineh.monitoring.controller.team;


import github.oineh.monitoring.controller.team.req.TeamCreateIpReq;
import github.oineh.monitoring.controller.team.req.TeamCreatePortReq;
import github.oineh.monitoring.controller.team.req.TeamCreateUrlReq;
import github.oineh.monitoring.controller.team.res.TeamInDominRes;
import github.oineh.monitoring.controller.team.res.TeamInMemberRes;
import github.oineh.monitoring.domain.connect.ConnectService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class ApiTeamController {

    private final ConnectService connectService;


    @PostMapping("/find/domain/{teamId}")
    public ResponseEntity<List<TeamInDominRes>> findTeamDomain(@PathVariable("teamId") Long teamId,
        Principal principal) {
        List<TeamInDominRes> res = connectService.findTeamInConnectList(teamId, principal.getName());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/find/member/{teamId}")
    public ResponseEntity<List<TeamInMemberRes>> findTeam(@PathVariable("teamId") Long teamId,
        Principal principal) {
        List<TeamInMemberRes> res = connectService.findTeamInConnectMemberList(teamId, principal.getName());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/add/url")
    public ResponseEntity<Void> createUrl(TeamCreateUrlReq req, Principal principal) {
        connectService.createUrl(req, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/ip/port")
    public ResponseEntity<Void> createPort(TeamCreatePortReq req, Principal principal) {
        connectService.createIpPort(req, principal.getName());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/add/ip")
    public ResponseEntity<Void> createIp(TeamCreateIpReq req, Principal principal) {
        connectService.createIp(req, principal.getName());

        return ResponseEntity.ok().build();

    }
}
