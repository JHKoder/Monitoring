package github.oineh.monitoring.controller.user.res;

import github.oineh.monitoring.domain.group.invit.InvitedGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InviteTeamUserRes {

    private Long id;
    private String teamName;
    private Long teamId;
    private String sendName;

    public InviteTeamUserRes(InvitedGroup invited) {
        this.id = invited.getId();
        this.teamName = invited.getTeam().getName();
        this.teamId = invited.getTeam().getId();
        this.sendName = invited.getSendUser().getInformation().getName();
    }
}
