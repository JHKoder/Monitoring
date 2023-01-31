package github.oineh.monitoring.groups.invite.domain;

import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.invit.domain.InvitedGroups;
import github.oineh.monitoring.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static github.oineh.monitoring.user.domain.User.Information;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("그룹 초대장")
public class InvitedGroupsTest {

    @Test
    @DisplayName("만들기")
    public void invite() {
        //given
        Information sendUserInfo = new Information("send_email@email.com", "pw", "s");
        Information targetUserInfo = new Information("target_email@email.com", "pw", "t");
        User sendUser = new User("id", "pw", sendUserInfo);
        User targetUser = new User("id", "pw", targetUserInfo);
        Groups groups = new Groups(sendUser, "");

        //when
        InvitedGroups invitedGroups = new InvitedGroups(sendUser, targetUser, groups);

        //then
        assertThat(invitedGroups).isNotNull();
    }
}
