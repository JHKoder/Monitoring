package github.oineh.monitoring.groups.group.dept.team.invit.web.res;

import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedTeam;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InviteTeamUserRes {

    private Long id;
    private String teamName;
    private Long teamId;
    private String sendName;


    public InviteTeamUserRes(InvitedTeam invited) {
        this.id = invited.getId();
        this.teamName = invited.getTeamName();
        this.teamId = invited.getTeamId();
        this.sendName = invited.getSendUserName();
    }
}
