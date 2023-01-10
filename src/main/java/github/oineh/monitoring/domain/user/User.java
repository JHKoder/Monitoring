package github.oineh.monitoring.domain.user;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.authority.Auth;
import github.oineh.monitoring.domain.groups.Groups;
import github.oineh.monitoring.domain.pc.Pc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String pw;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Pc pc;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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

    public void updateGroups(Groups groups) {
        this.groups.add(groups);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Information {

        private String email;
        private String name;
        private String nickName;
    }

    public void updatePc(Pc pc) {
        this.pc = pc;
    }
}
