package github.oineh.monitoring.groups.group.dept.team.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.connect.domain.Connect;
import github.oineh.monitoring.connect.domain.ConnectRepository;
import github.oineh.monitoring.connect.web.res.TeamInDomainPingRes;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.domain.Dept;
import github.oineh.monitoring.groups.group.dept.domain.DeptRepository;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.groups.group.dept.team.domain.TeamRepository;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteReq;
import github.oineh.monitoring.pc.domain.Type;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import github.oineh.monitoring.user.domain.UserRepository;
import io.github.tcp.network.Monitoring;
import io.github.tcp.network.NetStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 팀 컨트롤 테스트")
class ApiTeamAddressControllerTest extends IntegrationTest {

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

    String rest = "/api/team";

    User user;
    User adminUser;
    User targetUser;
    Groups groups;
    Dept dept;
    Team team;

    @BeforeEach
    void setup() {
        Information information = new Information("test_email_@test.com", "test_name", "test_Nickname");
        adminUser = userRepository.save(new User("test_admin_id", "password", information));

        Information userInfo = new Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));

        groups = groupsRepository.save(new Groups(adminUser, "group_name").updateMember(user));

        dept = deptRepository.save(new Dept(user, "dept_name"));
        team = teamRepository.save(new Team(user, "team_name"));
        dept.updateTeam(team);
        groups.updateDept(dept);
    }

    @Test
    @DisplayName("팀에서 외부 연결 상태를 알아야할 도메인을 리스트로 가져올수 있는지 테스트")
    void findGroup() throws Exception {
        //given
        Connect connectUrl1 = Connect.tcp("네이버", "https://naver.com");
        Connect connectUrl2 = Connect.tcp("구글", "https://google.com");
        team.updateConnect(connectUrl1);
        team.updateConnect(connectUrl2);

        //when
        ResultActions action = mvc.perform(get(rest + "/domain/" + team.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(connectUrl1.getName()))
                .andExpect(jsonPath("$[1].name").value(connectUrl2.getName()));
    }

    @Test
    @DisplayName("연결 체크를 할 팀원이 PC가 등록이 되어 있다면 리스트를 가져올수 있는지 테스트")
    void createDept() throws Exception {
        //given
        user.updatePc("강 pc", Type.PC);
        user.updateConnect(user.getNickName(), "127.0.0.1");
        team.updateMember(adminUser);
        adminUser.updatePc("김 pc", Type.PC);
        adminUser.updateConnect(user.getNickName(), "127.0.0.1");

        //when
        ResultActions action = mvc.perform(get(rest + "/member/" + team.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(user.getInformation().getName()))
                .andExpect(jsonPath("$[0].id").value(user.getPc().getId()))
                .andExpect(jsonPath("$[1].name").value(adminUser.getInformation().getName()))
                .andExpect(jsonPath("$[1].id").value(adminUser.getPc().getId()));
    }

    @Test
    @DisplayName("도메인의 연결 상태 확인 테스트")
    void pingDomain() throws Exception {
        //given
        Connect connectUrl = connectRepository.save(Connect.tcp("네이버", "https://naver.com"));
        team.updateConnect(connectUrl);

        when(mockMonitoringService.TcpStatus(any()))
                .thenReturn(tcp());

        //when
        ResultActions action = mvc.perform(
                get(rest + "/ping/domain/" + team.getId() + "/connect/" + connectUrl.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(connectUrl.getName()))
                .andExpect(jsonPath("$.status").value(new TeamInDomainPingRes().statusToStr(NetStatus.OK)))
                .andExpect(jsonPath("$.connectId").value(connectUrl.getId()));
    }

    @Test
    @DisplayName("팀원 연결 상태 확인 테스트")
    void pingMember() throws Exception {
        //given
        user.updatePc("강 pc", Type.PC);
        Connect connect = connectRepository.save(Connect.icmp("내ip", "127.0.0.1"));
        user.updateConnect(connect.getName(), connect.getIp());
        when(mockMonitoringService.TcpStatus(any()))
                .thenReturn(tcp());

        //when
        ResultActions action = mvc.perform(
                get(rest + "/ping/member/" + team.getId() + "/connect/" + connect.getId()));

        //then
        action.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getPc().getConnect().getId()))
                .andExpect(jsonPath("$.status").value(NetStatus.OK.name()))
                .andExpect(jsonPath("$.name").value(user.getInformation().getName()));
    }

    @Test
    @DisplayName("팀원 초대 테스트")
    void inviteTeam() throws Exception {
        //given
        TeamInviteReq req = new TeamInviteReq(team.getId(), groups.getId(), adminUser.getInformation().getEmail());

        //when
        ResultActions action = mvc.perform(post(rest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }


    private NetStatus tcp() {
        return NetStatus.OK;
    }

}
