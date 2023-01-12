package github.oineh.monitoring.controller.team.res;

import io.github.sno.network.NetStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamInMemberPingRes {

    private String name;
    private NetStatus status;
    private Long id;

    public TeamInMemberPingRes(Long id, String nickName, NetStatus status) {
        this.id = id;
        this.name = nickName;
        this.status = status;
    }
}
