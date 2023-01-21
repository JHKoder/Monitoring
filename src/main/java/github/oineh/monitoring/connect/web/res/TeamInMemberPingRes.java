package github.oineh.monitoring.connect.web.res;

import github.oineh.monitoring.user.domain.User;
import io.github.tcp.network.NetStatus;
import lombok.Getter;

@Getter
public class TeamInMemberPingRes {

    private String name;
    private NetStatus status;
    private Long id;

    public TeamInMemberPingRes() {
        System.out.println("---");
    }

    public TeamInMemberPingRes(User member, NetStatus status) {
        this.id = member.getConnectId();
        this.name = member.getName();
        this.status = status;
    }
}
