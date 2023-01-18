package github.oineh.monitoring.controller.team.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamInviteReq {

    private Long teamId;
    private Long groupsId;
    private String email;

}
