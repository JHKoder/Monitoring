package github.oineh.monitoring.controller.group.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupInviteReq {

    private Long groupsId;
    private String email;

}
