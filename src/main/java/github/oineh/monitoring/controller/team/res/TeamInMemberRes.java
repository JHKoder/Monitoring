package github.oineh.monitoring.controller.team.res;

import io.github.sno.network.NetStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamInMemberRes {

    private String name;
    private NetStatus status;

    public TeamInMemberRes(String nickName, NetStatus status) {
        this.name = nickName;
        this.status = status;
    }
}
