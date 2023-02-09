package github.oineh.monitoring.connect.web.res;

import github.oineh.monitoring.user.domain.User;
import io.github.tcp.network.NetStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberPingResponse {

    private String name;
    private NetStatus status;
    private Long id;

    public MemberPingResponse(User member, NetStatus status) {
        this.id = member.getConnectId();
        this.name = member.getName();
        this.status = status;
    }
}
