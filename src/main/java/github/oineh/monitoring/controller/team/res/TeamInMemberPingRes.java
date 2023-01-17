package github.oineh.monitoring.controller.team.res;

import github.oineh.monitoring.domain.user.User;
import io.github.tcp.network.NetStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamInMemberPingRes {

    private String name;
    private NetStatus status;
    private Long id;

    public TeamInMemberPingRes(User member, NetStatus status) {
        this.id = member.getPc().getConnect().getId();
        this.name = member.getInformation().getName();
        this.status = status;
    }
}
