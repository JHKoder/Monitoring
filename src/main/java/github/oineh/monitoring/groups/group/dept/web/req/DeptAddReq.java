package github.oineh.monitoring.groups.group.dept.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeptAddReq {

    private String name;
    private Long groupsId;
}
