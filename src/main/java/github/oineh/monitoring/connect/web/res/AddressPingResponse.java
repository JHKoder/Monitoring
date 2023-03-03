package github.oineh.monitoring.connect.web.res;

import io.github.tcp.network.NetStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddressPingResponse {

    private String name;
    private String address;
    private String status;
    private Long connectId;

    public AddressPingResponse(Long connectId, String address, String name, NetStatus status) {
        this.connectId = connectId;
        this.address = address;
        this.name = name;
        this.status = statusToStr(status);
    }

    public String statusToStr(NetStatus status) {
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
