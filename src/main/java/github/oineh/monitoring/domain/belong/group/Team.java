package github.oineh.monitoring.domain.belong.group;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.user.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User adminUser;

    private String name;

    public Team(User adminUser, String name) {
        this.adminUser = adminUser;
        this.name = name;
    }

}
