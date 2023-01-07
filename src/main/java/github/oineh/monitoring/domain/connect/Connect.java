package github.oineh.monitoring.domain.connect;


import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Connect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ip;
    private String url;
    private int port;
    private ConnectType connectType;

    public Connect(String ip, String url, int port, ConnectType connectType) {

        this.ip = ip;
        this.url = url;
        this.port = port;
        this.connectType = connectType;
    }

    public static Connect icmp(String ip) {
        return new Connect(ip, null, 0, ConnectType.ICMP);
    }

    public static Connect tcp(String ip, int port) {
        return new Connect(ip, null, port, ConnectType.TCP_PORT);
    }

    public static Connect tcp(String url) {
        return new Connect(null, url, 0, ConnectType.TCP_URL);
    }

}
