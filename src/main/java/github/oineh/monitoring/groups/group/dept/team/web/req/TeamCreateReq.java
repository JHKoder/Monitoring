package github.oineh.monitoring.groups.group.dept.team.web.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamCreateReq {

    private Long groupsId;
    private Long deptId;
    private String name;
}
