package github.oineh.monitoring.domain.groups;


import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.domain.group.category.Large;
import github.oineh.monitoring.domain.group.category.Medium;
import github.oineh.monitoring.domain.group.category.Team;
import github.oineh.monitoring.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Groups extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private User createUser;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> memberUser = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Large large;

    @OneToOne(fetch = FetchType.LAZY)
    private Medium medium;

    @OneToOne(fetch = FetchType.LAZY)
    private Team team;

    public Groups(User user, String name) {
        this.createUser = user;
        this.name = name;
    }

    public Groups(String name, Large large, Medium medium, Team team) {
        this.name = name;
        this.large = large;
        this.medium = medium;
        this.team = team;
    }

    public void updateMember(User user) {
        this.memberUser.add(user);
    }
}
