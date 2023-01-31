package github.oineh.monitoring.user.domain;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.pc.domain.Pc;
import github.oineh.monitoring.pc.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String password;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Pc pc;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Groups> groups = new ArrayList<>();
    @Embedded
    private Information information;


    public User(String loginId, String password, Information information) {
        this.loginId = loginId;
        this.password = password;
        this.information = information;
    }

    public boolean hasPc() {
        return !isNull(pc);
    }

    public boolean isSameConnectId(Long connectId) {
        return this.getPc().isSameConnectId(connectId);
    }

    public Connect getConnect() {
        if (isNull(pc)) {
            return null;
        }
        return this.pc.getConnect();
    }

    public String getName() {
        return information.getName();
    }

    public Long getConnectId() {
        return pc.getConnectId();
    }

    public String getNickName() {
        return information.getNickName();
    }

    public void updateConnect(String nickname, String host) {
        pc.updateConnect(nickname, host);
    }

    public void updateConnect(Connect connect) {
        pc.updateConnect(connect);
    }

    public String getEmail() {
        return information.getEmail();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Information {

        @Column(unique = true)
        private String email;
        private String name;
        private String nickName;
    }

    public void updatePc(String nickname, Type type) {
        this.pc = new Pc(nickname, type);
    }
}
