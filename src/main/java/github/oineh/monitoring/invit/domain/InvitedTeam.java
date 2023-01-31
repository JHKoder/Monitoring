package github.oineh.monitoring.invit.domain;


import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class InvitedTeam extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User targetUser;
    @OneToOne(fetch = FetchType.LAZY)
    private User sendUser;
    @OneToOne(fetch = FetchType.LAZY)
    private Team team;

    public InvitedTeam(User targetUser, User sendUser, Team team) {
        this.targetUser = targetUser;
        this.sendUser = sendUser;
        this.team = team;
    }

    public String getSendUserName() {
        return this.sendUser.getName();
    }

    public Long getTeamId() {
        return team.getId();
    }

    public String getTeamName() {
        return team.getName();
    }
}
