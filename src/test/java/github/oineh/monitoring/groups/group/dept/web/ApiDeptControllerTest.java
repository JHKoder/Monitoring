package github.oineh.monitoring.groups.group.dept.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.web.req.DeptAddReq;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import github.oineh.monitoring.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 부서 테스트")
public class ApiDeptControllerTest extends IntegrationTest {

    String rest = "/api/dept";
    User user;
    Groups groups;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;


    @BeforeEach
    void setup() {
        Information information = new Information("test_email_@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", information));
        groups = groupsRepository.save(new Groups(user, "group_name"));
    }

    @Test
    @DisplayName("부서 생성 테스트")
    void createDept() throws Exception {
        //given
        DeptAddReq req = new DeptAddReq("부서이름", groups.getId());

        //when
        ResultActions action = mvc.perform(post(rest)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }
}
