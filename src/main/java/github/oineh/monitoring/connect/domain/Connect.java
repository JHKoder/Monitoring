package github.oineh.monitoring.connect.domain;


import github.oineh.monitoring.common.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

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

    public boolean isSameId(Long id) {
        return Objects.equals(this.id, id);
    }
}
