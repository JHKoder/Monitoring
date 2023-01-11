package github.oineh.monitoring.controller.user.res;

import github.oineh.monitoring.domain.groups.invit.InvitedGroups;
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
        this.groupsName = groups.getGroups().getName();
        this.groupsId = groups.getGroups().getId();
        this.sendName = groups.getSendUser().getInformation().getName();
    }
}
