package github.oineh.monitoring.groups.group.dept.domain;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Dept extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User adminUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();
    private String name;


    public Dept(User adminUser, String name) {
        this.adminUser = adminUser;
        this.name = name;
    }

    public void updateTeam(Team team) {
        this.teams.add(team);
    }
}
