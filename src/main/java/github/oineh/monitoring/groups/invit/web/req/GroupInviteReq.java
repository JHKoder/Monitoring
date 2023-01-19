package github.oineh.monitoring.groups.invit.web.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInviteReq {

    private Long inviteId;
    private Long groupsId;
}
