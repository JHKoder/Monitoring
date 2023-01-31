package github.oineh.monitoring.team.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.team.web.rest.req.AddressCreateIpRequest;
import github.oineh.monitoring.team.web.rest.req.AddressCreatePortRequest;
import github.oineh.monitoring.team.web.rest.req.AddressCreateUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AddressService {

    private final TeamRepository teamRepository;

    @Transactional
    public void createUrl(AddressCreateUrlRequest req) {
        findTeam(req.getTeamId())
                .updateConnect(Connect.tcp(req.getName(), req.filterUrl()));
    }

    @Transactional
    public void createIpPort(AddressCreatePortRequest req) {
        findTeam(req.getTeamId())
                .updateConnect(Connect.tcp(req.getPortName(), req.getIp(), req.getPort()));
    }

    @Transactional
    public void createIp(AddressCreateIpRequest req) {
        findTeam(req.getTeamId())
                .updateConnect(Connect.icmp(req.getName(), req.getIp()));
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));
    }
}
