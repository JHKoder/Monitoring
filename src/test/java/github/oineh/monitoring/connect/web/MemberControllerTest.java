package github.oineh.monitoring.connect.web;

import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.connect.domain.ConnectRepository;
import github.oineh.monitoring.department.domain.Department;
import github.oineh.monitoring.department.domain.DepartmentRepository;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.pc.domain.Type;
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

import static github.oineh.monitoring.user.domain.User.Information;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 맴버 연결 ")
public class MemberControllerTest extends IntegrationTest {

    private final String url = "/api/member/teams";
    User user;
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
        Information userInfo = new Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));
        groups = groupsRepository.save(new Groups(user, "group_name"));
        dept = departmentRepository.save(new Department(user, "dept_name"));
        team = teamRepository.save(new Team(user, "team_name"));
    }

    @Test
    @DisplayName("확인할 리스트 가져오기")
    void createDept() throws Exception {
        //given
        user.updatePc("강 pc", Type.PC);
        user.updateConnect(user.getNickName(), "127.0.0.1");

        //when
        ResultActions action = mvc.perform(get(url + "/" + team.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(user.getInformation().getName()))
                .andExpect(jsonPath("$[0].id").value(user.getPc().getId()));
    }


    @Test
    @DisplayName("상태 확인")
    void pingMember() throws Exception {
        //given
        Connect connect = connectRepository.save(Connect.icmp("내ip", "127.0.0.1"));
        user.updatePc("강 pc", Type.PC);
        user.updateConnect(connect);
        when(mockMonitoringService.IcmpStatus(any()))
                .thenReturn(tcp());

        //when
        ResultActions action = mvc.perform(
                get(url + "/" + team.getId() + "/connects/" + connect.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getConnectId()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }


    private NetStatus tcp() {
        return NetStatus.OK;
    }
}
