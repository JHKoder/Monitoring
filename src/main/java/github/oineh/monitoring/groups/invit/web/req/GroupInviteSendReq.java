package github.oineh.monitoring.groups.invit.web.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInviteSendReq {

    private Long groupsId;
    private String email;
}
