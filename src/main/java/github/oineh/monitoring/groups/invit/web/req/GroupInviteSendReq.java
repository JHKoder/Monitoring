package github.oineh.monitoring.groups.invit.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInviteSendReq {

    private Long groupsId;
    private String email;
}
