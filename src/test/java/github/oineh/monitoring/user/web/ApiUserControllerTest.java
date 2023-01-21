package github.oineh.monitoring.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.groups.group.dept.team.domain.TeamRepository;
import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedGroupRepository;
import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedTeam;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteAcceptReq;
import github.oineh.monitoring.groups.invit.domain.InvitedGroupsRepository;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import github.oineh.monitoring.user.domain.UserRepository;
import github.oineh.monitoring.user.web.req.SignUpReq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 유저 컨트롤 테스트")
class ApiUserControllerTest extends IntegrationTest {

    static final String TARGET_RESOURCE = "/api/user";

    User adminUser;
    Groups groups;
    User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private InvitedGroupsRepository invitedGroupsRepository;
    @Autowired
    private InvitedGroupRepository invitedGroupRepository;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setup() {
        Information information = new Information("test_email_@test.com", "test_name", "test_Nickname");
        adminUser = userRepository.save(new User("test_admin_id", "password", information));

        Information userInfo = new Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));

        groups = groupsRepository.save(new Groups(adminUser, "group_name"));
    }

    @AfterEach
    void after() {
        invitedGroupRepository.deleteAll();
        invitedGroupsRepository.deleteAll();
        userRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    @DisplayName("팀 초대 리스트 테스트")
    void findTeamInviteList() throws Exception {
        //given
        Team team = teamRepository.save(new Team(adminUser, "team_name"));
        InvitedTeam invited = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team));

        //when
        ResultActions action = mvc.perform(get(TARGET_RESOURCE + "/team/invite"));

        //then
        action.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(invited.getId()))
                .andExpect(jsonPath("$[0].teamName").value(invited.getTeam().getName()))
                .andExpect(jsonPath("$[0].teamId").value(invited.getTeam().getId()))
                .andExpect(jsonPath("$[0].sendName").value(invited.getSendUser().getInformation().getName()));
    }

    @Test
    @DisplayName("팀 초대 수락 테스트")
    void acceptTeamInvite() throws Exception {
        //given
        Team team = teamRepository.save(new Team(adminUser, "team_name"));
        InvitedTeam invited = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team));

        TeamInviteAcceptReq req = new TeamInviteAcceptReq(invited.getId(), team.getId());

        //when
        ResultActions action = mvc.perform(
                post(TARGET_RESOURCE + "/team/invite").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("팀 초대 거부 테스트")
    void cancelTeamInvite() throws Exception {
        //given
        Team team = teamRepository.save(new Team(adminUser, "team_name"));
        InvitedTeam invited = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team));
        TeamInviteAcceptReq req = new TeamInviteAcceptReq(invited.getId(), team.getId());

        //when
        ResultActions action = mvc.perform(
                delete(TARGET_RESOURCE + "/team/invite").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 테스트")
    void singUp() throws Exception {
        //given
        Information info = user.getInformation();
        SignUpReq req = new SignUpReq(info.getEmail() + "Co", info.getName() + "Na", info.getNickName() + "NI",
                user.getLoginId() + "ID", user.getPw() + "!@");

        //when
        ResultActions action = mvc.perform(post(TARGET_RESOURCE + "/signup").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }
}
