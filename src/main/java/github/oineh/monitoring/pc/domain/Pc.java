package github.oineh.monitoring.pc.domain;


import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.connect.domain.Connect;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Pc extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Connect connect;
    private String name;
    private Type pcType;

    public Pc(String name, Type pcType) {
        this.name = name;
        this.pcType = pcType;
    }

    public boolean isSameConnectId(Long connectId) {
        return this.getConnect().isSameId(connectId);
    }

    public Long getConnectId() {
        return connect.getId();
    }

    public String getConnectHost() {
        return connect.getIp();
    }

    public String getConnectNickName() {
        return connect.getName();
    }

    public void updateConnect(String nickName, String host) {
        this.connect = Connect.icmp(nickName, host);
    }

    public void updateConnect(String nickName, String ip, int port) {
        this.connect = Connect.tcp(nickName, ip, port);
    }

    public void updateConnectUrl(String name, String url) {
        this.connect = Connect.tcp(name, url);
    }

    public void updateConnect(Connect connect) {
        this.connect = connect;
    }
}
