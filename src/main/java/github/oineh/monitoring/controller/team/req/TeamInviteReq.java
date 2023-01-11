package github.oineh.monitoring.controller.team.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamInviteReq {

    private Long teamId;
    private Long groupsId;
    private String email;

}
