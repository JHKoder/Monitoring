package github.oineh.monitoring.team.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.team.web.req.TeamCreateIpReq;
import github.oineh.monitoring.team.web.req.TeamCreatePortReq;
import github.oineh.monitoring.team.web.req.TeamCreateUrlReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AddressService {

    private final TeamRepository teamRepository;


    @Transactional
    public void createUrl(TeamCreateUrlReq req) {
        findTeam(req.getTeamId())
                .updateConnect(Connect.tcp(req.getName(), req.filterUrl()));
    }

    @Transactional
    public void createIpPort(TeamCreatePortReq req) {
        findTeam(req.getTeamId())
                .updateConnect(Connect.tcp(req.getName(), req.getIp(), req.getPort()));
    }

    @Transactional
    public void createIp(TeamCreateIpReq req) {
        findTeam(req.getTeamId())
                .updateConnect(Connect.icmp(req.getName(), req.getIp()));
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));
    }

}
