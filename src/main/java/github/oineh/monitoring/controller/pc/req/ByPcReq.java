package github.oineh.monitoring.controller.pc.req;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ByPcReq {

    private String ip;
    private String url;
    private int port;


    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }
}
