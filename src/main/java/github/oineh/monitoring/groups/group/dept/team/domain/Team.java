package github.oineh.monitoring.groups.group.dept.team.domain;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User createUser;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> members = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Connect> connects = new ArrayList<>();
    private String name;

    public Team(User createUser, String name) {
        this.members.add(createUser);
        this.createUser = createUser;
        this.name = name;
    }

    public void updateMember(User user) {
        this.members.add(user);
    }

    public void updateConnect(Connect connect) {
        this.connects.add(connect);
    }
}
