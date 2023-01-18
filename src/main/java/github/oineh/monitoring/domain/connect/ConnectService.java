package github.oineh.monitoring.domain.connect;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.controller.team.req.TeamCreateIpReq;
import github.oineh.monitoring.controller.team.req.TeamCreatePortReq;
import github.oineh.monitoring.controller.team.req.TeamCreateUrlReq;
import github.oineh.monitoring.controller.team.res.TeamInDomainPingRes;
import github.oineh.monitoring.controller.team.res.TeamInDomainRes;
import github.oineh.monitoring.controller.team.res.TeamInMemberPingRes;
import github.oineh.monitoring.controller.team.res.TeamInMemberRes;
import github.oineh.monitoring.domain.groups.group.category.Team;
import github.oineh.monitoring.domain.groups.group.category.TeamRepository;
import github.oineh.monitoring.domain.user.User;
import io.github.tcp.network.NetStatus;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectService {

    private final TeamRepository teamRepository;
    private final NetStatusFactory netStatusFactory;


    @Transactional
    public TeamInDomainPingRes findTeamInConnectDomain(Long teamId, Long connectId) {
        return findTeam(teamId).getConnects().stream()
            .filter(connect -> connect.isSameId(connectId))
            .findFirst()
            .map(connect -> new TeamInDomainPingRes(connectId, connect.getName(), connectStatus(connect)))
            .orElse(new TeamInDomainPingRes());
    }

    @Transactional
    public TeamInMemberPingRes findTeamInConnectMemberList(Long teamId, Long connectId) {
        return findTeam(teamId).getMembers().stream()
            .filter(User::hasPc)
            .filter(member -> member.isSameConnectId(connectId))
            .findFirst()
            .map(member -> new TeamInMemberPingRes(member, connectStatus(member.getConnect())))
            .orElse(new TeamInMemberPingRes());
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


    @Transactional
    public List<TeamInDomainRes> findTeamInDomain(Long teamId) {
        return findTeam(teamId).getConnects()
            .stream()
            .filter(connect -> connect.getConnectType() != null)
            .map(TeamInDomainRes::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<TeamInMemberRes> findTeamInMember(Long teamId) {
        return findTeam(teamId).getMembers().stream()
            .filter(member -> member.getPc() != null)
            .map(TeamInMemberRes::new)
            .collect(Collectors.toList());
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));
    }


    private NetStatus connectStatus(Connect connect) {
        return netStatusFactory.factory(connect.getConnectType()).apply(connect);
    }
}
