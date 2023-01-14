package github.oineh.monitoring.controller.groups.group.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateTeamDeptReq {

    private String name;
    private Long groupsId;
}
