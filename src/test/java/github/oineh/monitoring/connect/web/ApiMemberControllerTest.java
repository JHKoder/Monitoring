package github.oineh.monitoring.connect.web;

import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.connect.domain.ConnectRepository;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.domain.Dept;
import github.oineh.monitoring.groups.group.dept.domain.DeptRepository;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.groups.group.dept.team.domain.TeamRepository;
import github.oineh.monitoring.pc.domain.Type;
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

public class ApiMemberControllerTest extends IntegrationTest {


    @Mock
    Monitoring mockMonitoringService;

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

    String rest = "/api/member/teams";

    User user;
    Groups groups;
    Dept dept;
    Team team;


    @BeforeEach
    void setup() {
        Information userInfo = new Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));
        groups = groupsRepository.save(new Groups(user, "group_name"));
        dept = deptRepository.save(new Dept(user, "dept_name"));
        team = teamRepository.save(new Team(user, "team_name"));
    }

    @Test
    @DisplayName("연결 체크를 할 팀원이 PC가 등록이 되어 있다면 리스트를 가져올수 있는지 테스트")
    void createDept() throws Exception {
        //given
        user.updatePc("강 pc", Type.PC);
        user.updateConnect(user.getNickName(), "127.0.0.1");

        //when
        ResultActions action = mvc.perform(get(rest + "/" + team.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(user.getInformation().getName()))
                .andExpect(jsonPath("$[0].id").value(user.getPc().getId()));
    }


    @Test
    @DisplayName("팀원 연결 상태 확인 테스트")
    void pingMember() throws Exception {
        //given
        Connect connect = connectRepository.save(Connect.icmp("내ip", "127.0.0.1"));
        user.updatePc("강 pc", Type.PC);
        user.updateConnect(connect);
        when(mockMonitoringService.IcmpStatus(any()))
                .thenReturn(tcp());

        //when
        ResultActions action = mvc.perform(
                get(rest + "/" + team.getId() + "/connects/" + connect.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getConnectId()))
                .andExpect(jsonPath("$.name").value(user.getName()));
    }


    private NetStatus tcp() {
        return NetStatus.OK;
    }
}
