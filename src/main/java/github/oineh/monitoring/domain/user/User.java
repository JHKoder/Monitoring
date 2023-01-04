package github.oineh.monitoring.domain.user;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.authority.Auth;
import github.oineh.monitoring.domain.belong.Groups;
import github.oineh.monitoring.domain.pc.Pc;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @OneToOne(fetch = FetchType.LAZY)
    private Pc pc;
    @OneToOne(fetch = FetchType.LAZY)
    private Groups groups;
    @OneToOne(fetch = FetchType.LAZY)
    private Auth auth;

    @Embedded
    private Information information;

    public User(String loginId, String pw, Groups groups, Information information) {
        this.loginId = loginId;
        this.pw = pw;
        this.groups = groups;
        this.information = information;
    }

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

    public void updateGroup(Groups groups) {
        this.groups = groups;
    }

}
