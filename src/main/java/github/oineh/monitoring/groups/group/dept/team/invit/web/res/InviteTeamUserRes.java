package github.oineh.monitoring.groups.group.dept.team.invit.web.res;

import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedTeam;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InviteTeamUserRes {

    private Long inviteId;
    private Long teamId;
    private String groupName;
    private String sendName;


    public InviteTeamUserRes(InvitedTeam invited) {
        this.inviteId = invited.getId();
        this.groupName = invited.getTeamName();
        this.teamId = invited.getTeamId();
        this.sendName = invited.getSendUserName();
    }
}
