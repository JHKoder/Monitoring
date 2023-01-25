package github.oineh.monitoring.groups.invit.domain;


import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class InvitedGroups extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User targetUser;
    @OneToOne(fetch = FetchType.LAZY)
    private User sendUser;
    @OneToOne(fetch = FetchType.LAZY)
    private Groups groups;


    public InvitedGroups(User targetUser, User sendUser, Groups groups) {
        this.targetUser = targetUser;
        this.sendUser = sendUser;
        this.groups = groups;
    }

    public String getSendUserName() {
        return sendUser.getName();
    }

    public Long getGroupsId() {
        return groups.getId();
    }

    public String getGroupsName() {
        return groups.getName();
    }

    public boolean targetUserEquals(User targetUser) {
        Long thisUserId = targetUser.getId();
        return thisUserId.equals(this.targetUser.getId());
    }
}
