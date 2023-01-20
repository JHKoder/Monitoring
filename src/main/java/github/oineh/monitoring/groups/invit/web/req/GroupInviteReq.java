package github.oineh.monitoring.groups.invit.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInviteReq {

    private Long inviteId;
    private Long groupsId;
}
