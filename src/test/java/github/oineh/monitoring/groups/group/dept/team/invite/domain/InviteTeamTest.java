package github.oineh.monitoring.groups.group.dept.team.invite.domain;

import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedTeam;
import github.oineh.monitoring.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static github.oineh.monitoring.user.domain.User.Information;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("entity 팀 초대 ")
public class InviteTeamTest {

    @Test
    @DisplayName("팀 초대 생성")
    public void invite() {
        //given
        Information sendUserInfo = new Information("send_email@email.com", "pw", "s");
        Information targetUserInfo = new Information("target_email@email.com", "pw", "t");
        User sendUser = new User("id", "pw", sendUserInfo);
        User targetUser = new User("id", "pw", targetUserInfo);
        Team team = new Team(sendUser, "");

        //when
        InvitedTeam invitedGroups = new InvitedTeam(sendUser, targetUser, team);

        //then
        assertThat(invitedGroups).isNotNull();
    }
}
