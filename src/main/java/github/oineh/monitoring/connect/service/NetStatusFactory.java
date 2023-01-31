package github.oineh.monitoring.connect.service;

import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.connect.domain.ConnectType;
import io.github.tcp.network.Host;
import io.github.tcp.network.Monitoring;
import io.github.tcp.network.NetStatus;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class NetStatusFactory {

    private final Map<ConnectType, Function<Connect, NetStatus>> map;

    public NetStatusFactory(Monitoring monitoring) {
        this.map = Map.of(
                ConnectType.ICMP, connect -> monitoring.IcmpStatus(Host.from(connect.getIp())),
                ConnectType.TCP_PORT, connect -> monitoring.TcpStatus(Host.from(connect.getIp()), connect.getPort()),
                ConnectType.TCP_URL, connect -> monitoring.TcpStatus(connect.getUrl())
        );
    }

    public Function<Connect, NetStatus> factory(ConnectType type) {
        return map.entrySet().stream()
                .filter(entry -> entry.getKey() == type)
                .findFirst()
                .map(Entry::getValue)
                .orElseThrow(() -> new IllegalArgumentException("해당 커넥션은 지원되지 않습니다."));
    }
}
