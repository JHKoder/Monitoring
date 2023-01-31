package github.oineh.monitoring.invit.web.res;

import github.oineh.monitoring.invit.domain.InvitedTeam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InviteTeamResponse {

    private Long inviteId;
    private Long teamId;
    private String teamName;
    private String sendName;

    public InviteTeamResponse(InvitedTeam invited) {
        this.inviteId = invited.getId();
        this.teamId = invited.getTeamId();
        this.teamName = invited.getTeamName();
        this.sendName = invited.getSendUserName();
    }
}
