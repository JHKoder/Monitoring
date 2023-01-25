package github.oineh.monitoring.connect.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.connect.web.res.TeamInDomainPingRes;
import github.oineh.monitoring.connect.web.res.TeamInDomainRes;
import github.oineh.monitoring.connect.web.res.TeamInMemberPingRes;
import github.oineh.monitoring.connect.web.res.TeamInMemberRes;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.team.web.req.TeamCreateIpReq;
import github.oineh.monitoring.team.web.req.TeamCreatePortReq;
import github.oineh.monitoring.team.web.req.TeamCreateUrlReq;
import github.oineh.monitoring.user.domain.User;
import io.github.tcp.network.NetStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ConnectService {

    private final TeamRepository teamRepository;
    private final NetStatusFactory netStatusFactory;


    @Transactional(readOnly = true)
    public TeamInDomainPingRes findTeamInConnectDomain(Long teamId, Long connectId) {
        return findTeam(teamId).getConnects().stream()
                .filter(connect -> connect.isSameId(connectId))
                .findFirst()
                .map(connect -> new TeamInDomainPingRes(connectId, connect.getName(), connectStatus(connect)))
                .orElse(new TeamInDomainPingRes());
    }

    @Transactional(readOnly = true)
    public TeamInMemberPingRes findTeamInConnectMemberList(Long teamId, Long connectId) {
        return findTeam(teamId).getMembers().stream()
                .filter(User::hasPc)
                .filter(member -> member.isSameConnectId(connectId))
                .findFirst()
                .map(member -> new TeamInMemberPingRes(member, connectStatus(member.getConnect())))
                .orElse(new TeamInMemberPingRes());
    }

    @Transactional(readOnly = true)
    public List<TeamInDomainRes> findTeamInDomain(Long teamId) {
        return findTeam(teamId).getConnects()
                .stream()
                .filter(connect -> connect.getConnectType() != null)
                .map(TeamInDomainRes::new)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<TeamInMemberRes> findTeamInMember(Long teamId) {
        return findTeam(teamId).getMembers().stream()
                .filter(member -> member.getPc() != null)
                .map(TeamInMemberRes::new)
                .collect(toList());
    }

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

    private NetStatus connectStatus(Connect connect) {
        return netStatusFactory.factory(connect.getConnectType()).apply(connect);
    }
}
