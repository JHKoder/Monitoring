package github.oineh.monitoring.invit.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteTeamRequest {

    private Long teamId;
    private Long groupsId;
    private String email;
}
