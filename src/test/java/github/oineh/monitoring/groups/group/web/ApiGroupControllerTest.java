package github.oineh.monitoring.groups.group.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.groups.web.req.GroupsCreateReq;
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

@DisplayName("api 그룹")
public class ApiGroupControllerTest extends IntegrationTest {

    String url = "/api/group";

    User user;


    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setup() {
        User.Information information = new User.Information("test_email_@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", information));
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