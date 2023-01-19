package github.oineh.monitoring.connect.web;

import github.oineh.monitoring.connect.service.ConnectService;
import github.oineh.monitoring.connect.web.res.TeamInDomainPingRes;
import github.oineh.monitoring.connect.web.res.TeamInDomainRes;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address/teams")
@RequiredArgsConstructor
public class ApiAddressController {

    private final ConnectService connectService;


    @GetMapping("/{teamId}")
    public List<TeamInDomainRes> domainList(@PathVariable("teamId") Long teamId) {
        return connectService.findTeamInDomain(teamId);
    }

    @GetMapping("/{teamId}/connects/{connectId}")
    public TeamInDomainPingRes findTeamDomain(@PathVariable("teamId") Long teamId, @PathVariable Long connectId) {
        return connectService.findTeamInConnectDomain(teamId, connectId);
    }
}
