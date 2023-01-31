package github.oineh.monitoring.team.domain;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

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

    public boolean checkMember(User user) {
        return members.contains(user);
    }
}
