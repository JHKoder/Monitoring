package github.oineh.monitoring.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import github.oineh.monitoring.user.domain.UserRepository;
import github.oineh.monitoring.user.web.rest.req.AddSignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 유저")
class UserControllerTest extends IntegrationTest {

    final String url = "/api/user";
    User user;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        Information userInfo = new Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));
    }

    @Test
    @DisplayName("회원가입 하기")
    void singUp() throws Exception {
        //given
        Information info = user.getInformation();
        AddSignUpRequest req = new AddSignUpRequest(info.getEmail() + "Co", info.getName() + "Na", info.getNickName() + "NI",
                user.getLoginId() + "ID", user.getPassword() + "!@");

        //when
        ResultActions action = mvc.perform(post(url + "/signup").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }
}
