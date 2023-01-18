//package github.oineh.monitoring.controller.groups.group;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import github.oineh.monitoring.controller.common.ApiControllerTest;
//import github.oineh.monitoring.controller.groups.group.req.GroupCreateTeamDeptReq;
//import github.oineh.monitoring.domain.groups.Groups;
//import github.oineh.monitoring.domain.groups.group.category.Dept;
//import github.oineh.monitoring.domain.groups.group.category.Team;
//import github.oineh.monitoring.domain.user.domain.User;
//import github.oineh.monitoring.domain.user.domain.User.Information;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//
//@DisplayName("api 그룹 내부 컨트롤 테스트")
//public class ApiGroupControllerTest extends ApiControllerTest {
//
//
//    String rest = "/api/group";
//
//    User adminUser;
//    User targetUser;
//    Groups groups;
//
//    @BeforeEach
//    void setup() {
//        Information information = new Information("test_email_@test.com", "test_name", "test_Nickname");
//        adminUser = userRepository.save(new User("test_admin_id", "password", information));
//        Information targetInfo = new Information("test_email_target@test.com", "test_name", "test_Nickname");
//        targetUser = userRepository.save(new User("test_admin_id", "password", targetInfo));
//        groups = groupsRepository.save(new Groups(adminUser, "group_name"));
//        groups.updateMember(user);
//    }
//
//
//    @Test
//    @DisplayName("그룹 보기 테스트")
//    void findGroup() throws Exception {
//        //given
//        Dept dept = deptRepository.save(new Dept(user, "dept_name"));
//        Team team = teamRepository.save(new Team(user, "team_name"));
//        dept.updateTeam(team);
//        groups.updateDept(dept);
//
//        //when
//        ResultActions action = mvc.perform(get(rest + "/" + groups.getId()));
//
//        //then
//        action.andExpect(status().isOk())
//            .andExpect(jsonPath("$.groupsId").value(groups.getId()))
//            .andExpect(jsonPath("$.group[0].name").value(dept.getName()))
//            .andExpect(jsonPath("$.group[0].deptId").value(dept.getId()))
//            .andExpect(jsonPath("$.group[0].list[0].name").value(team.getName()))
//            .andExpect(jsonPath("$.group[0].list[0].teamId").value(team.getId()));
//
//    }
//
//    @Test
//    @DisplayName("부서 생성 테스트")
//    void createDept() throws Exception {
//        //given
//        GroupCreateTeamDeptReq req = new GroupCreateTeamDeptReq("부서이름", groups.getId());
//
//        //when
//        ResultActions action = mvc.perform(post(rest + "/dept")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(req)));
//
//        //then
//        action.andExpect(status().isOk());
//    }
//
//
//    @Test
//    @DisplayName("팀 생성 테스트")
//    void createTeam() throws Exception {
//        //given
//        GroupCreateTeamDeptReq req = new GroupCreateTeamDeptReq("부서이름", groups.getId());
//
//        //when
//        ResultActions action = mvc.perform(post(rest + "/dept")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(req)));
//
//        //then
//        action.andExpect(status().isOk());
//    }
//
//
//}
