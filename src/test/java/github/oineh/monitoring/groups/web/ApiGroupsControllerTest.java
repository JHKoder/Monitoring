package github.oineh.monitoring.groups.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.domain.Dept;
import github.oineh.monitoring.groups.group.dept.domain.DeptRepository;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.groups.group.dept.team.domain.TeamRepository;
import github.oineh.monitoring.groups.web.req.GroupsCreateReq;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import github.oineh.monitoring.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 그룹")
public class ApiGroupsControllerTest extends IntegrationTest {

    String url = "/api/groups";

    User user;
    User targetUser;
    Groups groups;


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private DeptRepository deptRepository;
    @Autowired
    private TeamRepository teamRepository;


    @BeforeEach
    void setup() {
        Information information = new Information("test_email_@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", information));
        Information targetInfo = new Information("test_email_target@test.com", "test_name", "test_Nickname");
        targetUser = userRepository.save(new User("test_target_id", "password", targetInfo));
        groups = groupsRepository.save(new Groups(user, "group_name"));
    }

    @Test
    @DisplayName("그룹 내 부서 & 팀 보기")
    void findGroup() throws Exception {
        //given
        Dept dept = deptRepository.save(new Dept(user, "dept"));
        Team team1 = teamRepository.save(new Team(user, "team_1"));
        Team team2 = teamRepository.save(new Team(user, "team_2"));
        groups.updateDept(dept);
        dept.updateTeam(team1);
        dept.updateTeam(team2);

        //when
        ResultActions action = mvc.perform(get(url + "/" + groups.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.groupsId").value(groups.getId()))
                .andExpect(jsonPath("$.group[0].name").value(dept.getName()))
                .andExpect(jsonPath("$.group[0].deptId").value(dept.getId()))
                .andExpect(jsonPath("$.group[0].list[0].name").value(team1.getName()))
                .andExpect(jsonPath("$.group[0].list[0].teamId").value(team1.getId()))
                .andExpect(jsonPath("$.group[0].list[1].name").value(team2.getName()))
                .andExpect(jsonPath("$.group[0].list[1].teamId").value(team2.getId()));
    }

    @Test
    @DisplayName("그룹들 리스트 보기")
    void findList() throws Exception {
        //given
        Groups groups1 = groupsRepository.save(new Groups(user, "groups_1"));
        Groups groups2 = groupsRepository.save(new Groups(user, "groups_2"));

        //when
        ResultActions action = mvc.perform(get(url));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].groupsId").value(groups.getId()))
                .andExpect(jsonPath("$[0].name").value(groups.getName()))
                .andExpect(jsonPath("$[1].groupsId").value(groups1.getId()))
                .andExpect(jsonPath("$[1].name").value(groups1.getName()))
                .andExpect(jsonPath("$[2].groupsId").value(groups2.getId()))
                .andExpect(jsonPath("$[2].name").value(groups2.getName()));
    }

    @Test
    @DisplayName("그룹 생성 하기")
    void createGroups() throws Exception {
        //given
        GroupsCreateReq req = new GroupsCreateReq("groupName");

        //when
        ResultActions action = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }
}
