package github.oineh.monitoring.dept.domain;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.team.domain.Team;
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

    public boolean isId(Long deptId) {
        return this.id.equals(deptId);
    }
}
