package github.oineh.monitoring.controller.team.req;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamCreateUrlReq {

    private Long teamId;
    private Long groupsId;
    private String name;
    private String url;

    public void updateUrlHttp() {
        this.url = "http://" + url;
    }

    public TeamCreateUrlReq filterUrl() {
        if (url.contains("://")) {
            updateUrlHttp();
        }
        return this;
    }
}
