package github.oineh.monitoring.groups.web.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupsInviteReq {

    private Long groupsId;
    private String targetEmail;
}
