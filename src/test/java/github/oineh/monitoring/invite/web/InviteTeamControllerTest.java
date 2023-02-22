package github.oineh.monitoring.invite.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.department.domain.Department;
import github.oineh.monitoring.department.domain.DepartmentRepository;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.invit.domain.InvitedGroupRepository;
import github.oineh.monitoring.invit.domain.InvitedTeam;
import github.oineh.monitoring.invit.web.req.InviteTeamAcceptRequest;
import github.oineh.monitoring.invit.web.req.InviteTeamRequest;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
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

@DisplayName("api 팀 초대장")
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
    @Autowired
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setup() {
        adminUser = createUser("test_email_@test.com", "test_admin_id");
        user = createUser("test@test.com", "test_user_id");
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

    private Team createTeam(User user) {
        Department department = departmentRepository.save(new Department(user, "department"));
        Team team = teamRepository.save(new Team(user, "team_name"));
        department.updateTeam(team);
        return team;
    }

    @Test
    @DisplayName("보내기")
    void teamInvite() throws Exception {
        //given
        Team team = createTeam(user);
        User targetUser = createUser("test_target_user@test.com", "test_target_user_id");
        groups.updateMember(targetUser);

        InviteTeamRequest inviteTeamRequest = new InviteTeamRequest(team.getId(), groups.getId(), targetUser.getEmail());

        //when
        ResultActions action = mvc.perform(
                post(url).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inviteTeamRequest)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("수락")
    void acceptTeamInvite() throws Exception {
        //given
        Team team = createTeam(adminUser);
        InvitedTeam invited = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team));
        InviteTeamAcceptRequest req = new InviteTeamAcceptRequest(invited.getId(), team.getId());

        //when
        ResultActions action = mvc.perform(
                patch(url).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("거부")
    void cancelTeamInvite() throws Exception {
        //given
        Team team = createTeam(adminUser);
        InvitedTeam invited = invitedGroupRepository.save(new InvitedTeam(user, adminUser, team));
        InviteTeamAcceptRequest req = new InviteTeamAcceptRequest(invited.getId(), team.getId());

        //when
        ResultActions action = mvc.perform(
                delete(url).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    private User createUser(String email, String id) {
        Information information = new Information(email, "test_name", "test_Nickname");
        return userRepository.save(new User(id, "password", information));
    }
}
