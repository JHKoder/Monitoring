package github.oineh.monitoring.connect.web.res;

import github.oineh.monitoring.user.domain.User;
import io.github.tcp.network.NetStatus;
import lombok.Getter;

@Getter
public class MemberPingResponse {

    private String name;
    private NetStatus status;
    private Long id;

    public MemberPingResponse() {
        System.out.println("---");
    }

    public MemberPingResponse(User member, NetStatus status) {
        this.id = member.getConnectId();
        this.name = member.getName();
        this.status = status;
    }
}
