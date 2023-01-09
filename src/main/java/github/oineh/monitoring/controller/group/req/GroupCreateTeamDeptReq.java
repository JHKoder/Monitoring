package github.oineh.monitoring.controller.group.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupCreateTeamDeptReq {

    private String name;
    private Long groupsId;
}
