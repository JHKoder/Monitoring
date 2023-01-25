package github.oineh.monitoring.team.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateReq {

    private Long groupsId;
    private Long deptId;
    private String name;
}