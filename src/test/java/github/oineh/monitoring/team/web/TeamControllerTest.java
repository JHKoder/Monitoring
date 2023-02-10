package github.oineh.monitoring.team.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.department.domain.Department;
import github.oineh.monitoring.department.domain.DepartmentRepository;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.team.web.rest.req.TeamCreateReq;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("api 팀")
public class TeamControllerTest extends IntegrationTest {

    static final String url = "/api/team";

    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    @Test
    @DisplayName("생성")
    public void creteTeam() throws Exception {
        //given
        User user = createUser();
        Groups groups = createGroups(user);
        Department department = createDepartment(groups, user);
        TeamCreateReq req = new TeamCreateReq(groups.getId(), department.getId(), "team_name");

        //when
        ResultActions action = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    private Department createDepartment(Groups groups, User user) {
        Department department = departmentRepository.save(new Department(user, "dept_name"));
        groups.updateDept(department);
        return department;
    }

    private Groups createGroups(User user) {
        return groupsRepository.save(new Groups(user, "group_name"));
    }

    private User createUser() {
        User.Information userInfo = new User.Information("test@test.com", "test_name", "test_Nickname");
        return userRepository.save(new User("test_user_id", "password", userInfo));
    }
}
