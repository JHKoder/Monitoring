package github.oineh.monitoring.controller.team.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamCreatePortReq {

    private Long teamId;

    private String name;
    private String url;
    private int port;

}
