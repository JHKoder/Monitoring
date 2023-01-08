package github.oineh.monitoring.controller.groups.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupsInviteReq {

    private Long groupsId;
    private String targetEmail;
}
