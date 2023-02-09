package github.oineh.monitoring.team.web.rest.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreatePortRequest {

    private Long teamId;
    private String portName;
    private int aClass;
    private int bClass;
    private int cClass;
    private int dClass;

    private int port;

    public String getIp() {
        valid();
        return aClass + "." + bClass + "." + cClass + "." + dClass;
    }

    private void valid() {
        if (ipSize(aClass) || ipSize(bClass) || ipSize(cClass) || ipSize(dClass)) {
            throw new IllegalArgumentException("IP 0~255 범위를 벗아났습니다.");
        }
    }

    public boolean ipSize(int num) {
        return !(num <= 255 && num >= 0);
    }
}
