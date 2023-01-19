package github.oineh.monitoring.groups.invit.web.res;

import github.oineh.monitoring.groups.invit.domain.InvitedGroups;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InviteGroupsUserRes {

    private Long id;
    private String groupsName;
    private Long groupsId;
    private String sendName;

    public InviteGroupsUserRes(InvitedGroups groups) {
        this.id = groups.getId();
        this.groupsName = groups.getGroupsName();
        this.groupsId = groups.getGroupsId();
        this.sendName = groups.getSendUserName();
    }
}
