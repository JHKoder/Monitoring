package github.oineh.monitoring.groups.group.dept.team.invit.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamInviteCancelReq {

    private Long teamId;
    private Long groupsId;
    private String email;
}
