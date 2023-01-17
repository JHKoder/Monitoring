package github.oineh.monitoring.controller.team.res;

import io.github.tcp.network.NetStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamInDomainPingRes {

    private String name;
    private String status;
    private Long connectId;

    public TeamInDomainPingRes(Long connectId, String name, NetStatus status) {
        this.connectId = connectId;
        this.name = name;
        this.status = statusToStr(status);
    }

    private String statusToStr(NetStatus status) {
        if (status == NetStatus.OK) {
            return "정상";
        }
        if (status == NetStatus.NOT_CONNECT) {
            return "연결 상태 미흡";
        }
        if (status == NetStatus.TIMEOUT) {
            return "연결 시간 초과";
        }
        return "";
    }


}
