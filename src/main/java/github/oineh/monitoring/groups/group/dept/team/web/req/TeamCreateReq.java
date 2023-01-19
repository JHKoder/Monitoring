package github.oineh.monitoring.groups.group.dept.team.web.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamCreateReq {

    private Long groupsId;
    private Long deptId;
    private String name;
}
