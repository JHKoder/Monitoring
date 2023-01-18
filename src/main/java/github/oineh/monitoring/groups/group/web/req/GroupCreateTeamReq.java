package github.oineh.monitoring.groups.group.web.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GroupCreateTeamReq {

    private Long groupsId;
    private Long deptId;
    private String name;
}
