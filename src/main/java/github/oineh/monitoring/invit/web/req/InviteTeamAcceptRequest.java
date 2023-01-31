package github.oineh.monitoring.invit.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteTeamAcceptRequest {

    private Long inviteId;
    private Long teamId;
}
