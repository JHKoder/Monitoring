package github.oineh.monitoring.groups.group.dept.team.invit.domain;


import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.user.domain.User;
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
public class InvitedGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User targetUser;

    @OneToOne(fetch = FetchType.LAZY)
    private User sendUser;

    @OneToOne(fetch = FetchType.LAZY)
    private Team team;

    public InvitedGroup(User targetUser, User sendUser, Team team) {
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
