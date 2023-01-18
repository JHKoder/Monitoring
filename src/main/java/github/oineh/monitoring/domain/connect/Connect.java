package github.oineh.monitoring.domain.connect;


import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Connect extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ip;
    private String url;
    private int port;
    @Enumerated
    private ConnectType connectType;

    public Connect(String name, String ip, String url, int port, ConnectType connectType) {
        this.name = name;
        this.ip = ip;
        this.url = url;
        this.port = port;
        this.connectType = connectType;
    }

    public static Connect icmp(String name, String ip) {
        return new Connect(name, ip, null, 0, ConnectType.ICMP);
    }

    public static Connect tcp(String name, String ip, int port) {
        return new Connect(name, ip, null, port, ConnectType.TCP_PORT);
    }

    public static Connect tcp(String name, String url) {
        return new Connect(name, null, url, 0, ConnectType.TCP_URL);
    }

    public boolean isIcmp() {
        return connectType == ConnectType.ICMP;
    }

    public boolean isTcpPort() {
        return connectType == ConnectType.TCP_PORT;
    }

    public boolean isTcpUrl() {
        return connectType == ConnectType.TCP_URL;
    }

    public boolean isSameId(Long id) {
        return Objects.equals(this.id, id);
    }
}
