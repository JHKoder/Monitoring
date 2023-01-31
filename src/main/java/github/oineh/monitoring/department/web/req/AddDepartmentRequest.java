package github.oineh.monitoring.department.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddDepartmentRequest {

    private String name;
    private Long groupsId;
}
