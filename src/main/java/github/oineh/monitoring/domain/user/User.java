package github.oineh.monitoring.domain.user;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.belong.Groups;
import github.oineh.monitoring.domain.pc.Pc;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
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

    @AllArgsConstructor
    public static class Information{
        private String email;
        private String name;
        private String nickName;
    }

    public void updatePc(Pc pc){
        this.pc=pc;
    }

    public void updateGroup(Groups groups){
        this.groups = groups;
    }

}
