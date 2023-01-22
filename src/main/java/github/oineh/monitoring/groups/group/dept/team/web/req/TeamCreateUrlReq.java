package github.oineh.monitoring.groups.group.dept.team.web.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateUrlReq {

    private Long teamId;
    private Long groupsId;
    private String name;
    private String url;


    public String filterUrl() {
        if (!url.contains("://")) {
            return String.format("http://%s", url);
        }
        return url;
    }
}
