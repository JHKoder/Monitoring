//package github.oineh.monitoring.controller.groups;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import github.oineh.monitoring.controller.common.ApiControllerTest;
//import github.oineh.monitoring.controller.groups.group.req.GroupInviteReq;
//import github.oineh.monitoring.controller.groups.req.GroupsCreateReq;
//import github.oineh.monitoring.domain.groups.Groups;
//import github.oineh.monitoring.domain.user.domain.User;
//import github.oineh.monitoring.domain.user.domain.User.Information;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//
//@DisplayName("api 그룹들 컨트롤 테스트")
//public class ApiGroupsControllerTest extends ApiControllerTest {
//
//    String rest = "/api/groups";
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
//    }
//
//    @Test
//    @DisplayName("그룹들 리스트 보기 테스트")
//    void findList() throws Exception {
//        //given
//        Groups groups1 = groupsRepository.save(new Groups(user, "groups_1"));
//        Groups groups2 = groupsRepository.save(new Groups(user, "groups_2"));
//
//        //when
//        ResultActions action = mvc.perform(get(rest));
//
//        //then
//        action.andExpect(status().isOk())
//            .andExpect(jsonPath("$[0].groupsId").value(groups1.getId()))
//            .andExpect(jsonPath("$[0].name").value(groups1.getName()))
//            .andExpect(jsonPath("$[1].name").value(groups2.getName()))
//            .andExpect(jsonPath("$[1].name").value(groups2.getName()));
//    }
//
//    @Test
//    @DisplayName("그룹 생성 테스트")
//    void createGroups() throws Exception {
//        //given
//        GroupsCreateReq req = new GroupsCreateReq("groupName");
//
//        //when
//        ResultActions action = mvc.perform(post(rest)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(req)));
//
//        //then
//        action.andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("그룹 초대 테스트")
//    void invite() throws Exception {
//        //given
//        groups.updateMember(user);
//        GroupInviteReq req = new GroupInviteReq(groups.getId(), targetUser.getInformation().getEmail());
//
//        //when
//        ResultActions action = mvc.perform(post(rest + "/invite")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(req)));
//
//        //then
//        action.andExpect(status().isOk());
//    }
//
//
//}
