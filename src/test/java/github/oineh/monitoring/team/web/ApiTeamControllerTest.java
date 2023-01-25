package github.oineh.monitoring.team.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.connect.domain.ConnectRepository;
import github.oineh.monitoring.dept.domain.Dept;
import github.oineh.monitoring.dept.domain.DeptRepository;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.team.web.req.TeamCreateReq;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("api 팀 테스트")
public class ApiTeamControllerTest extends IntegrationTest {

    static final String url = "/api/team";

    User user;
    Groups groups;
    Dept dept;
    Team team;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    DeptRepository deptRepository;
    @Autowired
    ConnectRepository connectRepository;


    @BeforeEach
    void setup() {
        User.Information userInfo = new User.Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));
        groups = groupsRepository.save(new Groups(user, "group_name"));
        dept = deptRepository.save(new Dept(user, "dept_name"));
        groups.updateDept(dept);
        team = teamRepository.save(new Team(user, "team_name"));
        dept.updateTeam(team);
    }

    @Test
    @DisplayName("팀 생성 테스트")
    public void creteTeam() throws Exception {
        //given
        TeamCreateReq req = new TeamCreateReq(groups.getId(), dept.getId(), "team_name");

        //when
        ResultActions action = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }
}
