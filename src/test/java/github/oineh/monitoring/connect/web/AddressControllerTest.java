package github.oineh.monitoring.connect.web;

import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.connect.domain.ConnectRepository;
import github.oineh.monitoring.connect.web.res.AddressPingResponse;
import github.oineh.monitoring.department.domain.Department;
import github.oineh.monitoring.department.domain.DepartmentRepository;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import io.github.tcp.network.Monitoring;
import io.github.tcp.network.NetStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 주소 연결")
public class AddressControllerTest extends IntegrationTest {

    private final String url = "/api/address/teams";
    User user;
    User adminUser;
    Groups groups;
    Department dept;
    Team team;

    @Mock
    Monitoring mockMonitoringService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    GroupsRepository groupsRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ConnectRepository connectRepository;


    @BeforeEach
    void setup() {
        User.Information information = new User.Information("test_email_@test.com", "test_name", "test_Nickname");
        adminUser = userRepository.save(new User("test_admin_id", "password", information));

        User.Information userInfo = new User.Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));

        groups = groupsRepository.save(new Groups(adminUser, "group_name").updateMember(user));

        dept = departmentRepository.save(new Department(user, "dept_name"));
        team = teamRepository.save(new Team(user, "team_name"));
        dept.updateTeam(team);
        groups.updateDept(dept);
    }


    @Test
    @DisplayName("확인할 리스트 가져오기")
    void findGroup() throws Exception {
        //given
        Connect connectUrl1 = Connect.tcp("네이버", "https://naver.com");
        Connect connectUrl2 = Connect.tcp("구글", "https://google.com");
        team.updateConnect(connectUrl1);
        team.updateConnect(connectUrl2);

        //when
        ResultActions action = mvc.perform(get(url + "/" + team.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(connectUrl1.getName()))
                .andExpect(jsonPath("$[1].name").value(connectUrl2.getName()));
    }

    @Test
    @DisplayName("상태 확인")
    void pingDomain() throws Exception {
        //given
        Connect connectUrl = connectRepository.save(Connect.tcp("네이버", "https://naver.com"));
        team.updateConnect(connectUrl);

        when(mockMonitoringService.TcpStatus(any()))
                .thenReturn(tcp());

        //when
        ResultActions action = mvc.perform(
                get(url + "/" + team.getId() + "/connects/" + connectUrl.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(connectUrl.getName()))
                .andExpect(jsonPath("$.status").value(new AddressPingResponse().statusToStr(NetStatus.OK)))
                .andExpect(jsonPath("$.connectId").value(connectUrl.getId()));
    }


    private NetStatus tcp() {
        return NetStatus.OK;
    }
}
