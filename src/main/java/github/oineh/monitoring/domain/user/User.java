package github.oineh.monitoring.domain.user;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.domain.authority.Auth;
import github.oineh.monitoring.domain.connect.Connect;
import github.oineh.monitoring.domain.groups.Groups;
import github.oineh.monitoring.domain.user.pc.Pc;
import github.oineh.monitoring.domain.user.pc.Type;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String pw;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Pc pc;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Groups> groups = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Auth auth;

    @Embedded
    private Information information;

    public User(String loginId, String pw, Information information) {
        this.loginId = loginId;
        this.pw = pw;
        this.information = information;
    }

    public User(String loginId, String pw, Information information, Auth auth) {
        this.loginId = loginId;
        this.pw = pw;
        this.information = information;
        this.auth = auth;
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
