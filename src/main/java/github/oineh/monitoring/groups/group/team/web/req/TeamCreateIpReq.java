package github.oineh.monitoring.groups.group.team.web.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamCreateIpReq {

    private Long teamId;

    private String name;

    private int a;
    private int b;
    private int c;
    private int d;

    public String getIp() {
        valid();
        return a + "." + b + "." + c + "." + d;
    }

    private void valid() {
        if (ipSize(a) || ipSize(b) || ipSize(c) || ipSize(d)) {
            System.out.println(a + "." + b + "." + c + "." + d);
            throw new IllegalArgumentException("IP 0~255 범위를 벗아났습니다.");
        }
    }

    public boolean ipSize(int num) {
        return !(num <= 255 && num >= 0);
    }
}
