package github.oineh.monitoring.connect.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.connect.web.res.AddressPingResponse;
import github.oineh.monitoring.connect.web.res.AddressResponse;
import github.oineh.monitoring.connect.web.res.MemberPingResponse;
import github.oineh.monitoring.connect.web.res.MemberResponse;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
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
    public AddressPingResponse findAddressPing(Long teamId, Long connectId) {
        return findTeam(teamId).getConnects().stream()
                .filter(connect -> connect.isSameId(connectId))
                .findFirst()
                .map(connect -> new AddressPingResponse(connectId, connect.getName(), connectStatus(connect)))
                .orElse(new AddressPingResponse());
    }

    @Transactional(readOnly = true)
    public MemberPingResponse findMemberPing(Long teamId, Long connectId) {
        return findTeam(teamId).getMembers().stream()
                .filter(User::hasPc)
                .filter(member -> member.isSameConnectId(connectId))
                .findFirst()
                .map(member -> new MemberPingResponse(member, connectStatus(member.getConnect())))
                .orElse(new MemberPingResponse());
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> findAddress(Long teamId) {
        return findTeam(teamId).getConnects()
                .stream()
                .filter(connect -> connect.getConnectType() != null)
                .map(AddressResponse::new)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findMember(Long teamId) {
        return findTeam(teamId).getMembers().stream()
                .filter(member -> member.getPc() != null)
                .map(MemberResponse::new)
                .collect(toList());
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));
    }

    private NetStatus connectStatus(Connect connect) {
        return netStatusFactory.factory(connect.getConnectType()).apply(connect);
    }
}
