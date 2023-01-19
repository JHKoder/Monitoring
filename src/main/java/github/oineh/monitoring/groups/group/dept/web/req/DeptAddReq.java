package github.oineh.monitoring.groups.group.dept.web.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptAddReq {

    private String name;
    private Long groupsId;
}
