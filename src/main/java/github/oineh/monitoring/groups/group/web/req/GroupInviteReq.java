package github.oineh.monitoring.groups.group.web.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInviteReq {

    private Long groupsId;
    private String email;

}
