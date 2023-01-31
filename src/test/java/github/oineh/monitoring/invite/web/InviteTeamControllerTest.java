package github.oineh.monitoring.invite.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.invit.domain.InvitedGroupRepository;
import github.oineh.monitoring.invit.domain.InvitedTeam;
import github.oineh.monitoring.invit.web.req.TeamInviteAcceptReq;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 팀 초대")
public class InviteTeamControllerTest extends IntegrationTest {

    static final String url = "/api/team/invite";

    User adminUser;
    Groups groups;
    User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private InvitedGroupRepository invitedGroupRepository;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setup() {
        User.Information information = new User.Information("test_email_@test.com", "test_name", "test_Nickname");
        adminUser = userRepository.save(new User("test_admin_id", "password", information));

        User.Information userInfo = new User.Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));

        groups = groupsRepository.save(new Groups(adminUser, "group_name"));
    }

    @Test
    @DisplayName("리스트 가져오기")
    void findTeamInviteList() throws Exception {
        //given
        Team team1 = teamRepository.save(new Team(adminUser, "team_name"));
        Team team2 = teamRepository.save(new Team(adminUser, "team_name2"));
        InvitedTeam invited1 = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team1));
        InvitedTeam invited2 = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team2));

        //when
        ResultActions action = mvc.perform(get(url));

        System.out.println(action.andReturn().getResponse().getContentAsString());
        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].inviteId").value(invited1.getId()))
                .andExpect(jsonPath("$[0].teamId").value(invited1.getTeamId()))
                .andExpect(jsonPath("$[0].teamName").value(invited1.getTeamName()))
                .andExpect(jsonPath("$[0].sendName").value(invited1.getSendUserName()))
                .andExpect(jsonPath("$[1].inviteId").value(invited2.getId()))
                .andExpect(jsonPath("$[1].teamId").value(invited2.getTeamId()))
                .andExpect(jsonPath("$[1].teamName").value(invited2.getTeamName()))
                .andExpect(jsonPath("$[1].sendName").value(invited2.getSendUserName()));
    }

    @Test
    @DisplayName("수락 하기")
    void acceptTeamInvite() throws Exception {
        //given
        Team team = teamRepository.save(new Team(adminUser, "team_name"));
        InvitedTeam invited = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team));

        TeamInviteAcceptReq req = new TeamInviteAcceptReq(invited.getId(), team.getId());

        //when
        ResultActions action = mvc.perform(
                patch(url).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("거부 하기")
    void cancelTeamInvite() throws Exception {
        //given
        Team team = teamRepository.save(new Team(adminUser, "team_name"));
        InvitedTeam invited = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team));
        TeamInviteAcceptReq req = new TeamInviteAcceptReq(invited.getId(), team.getId());

        //when
        ResultActions action = mvc.perform(
                delete(url).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }
}
