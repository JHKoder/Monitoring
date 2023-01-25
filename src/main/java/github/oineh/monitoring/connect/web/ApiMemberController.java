package github.oineh.monitoring.connect.web;

import github.oineh.monitoring.connect.service.ConnectService;
import github.oineh.monitoring.connect.web.res.TeamInMemberPingRes;
import github.oineh.monitoring.connect.web.res.TeamInMemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/member/teams")
@RequiredArgsConstructor
public class ApiMemberController {

    private final ConnectService connectService;


    @GetMapping("/{teamId}")
    public List<TeamInMemberRes> findMembers(@PathVariable("teamId") Long teamId) {
        return connectService.findTeamInMember(teamId);
    }

    @GetMapping("/{teamId}/connects/{connectId}")
    public TeamInMemberPingRes connectionMember(@PathVariable("teamId") Long teamId, @PathVariable Long connectId) {
        return connectService.findTeamInConnectMemberList(teamId, connectId);
    }
}
