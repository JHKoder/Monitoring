package github.oineh.monitoring.user.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import github.oineh.monitoring.user.domain.UserRepository;
import github.oineh.monitoring.user.web.rest.req.AddSignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 유저")
class UserControllerTest extends IntegrationTest {

    private final String URL = "/api/user";

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    void signUpSuccess() throws Exception {
        //given
        User user = createUser("test_user_id", "email@email.com");
        AddSignUpRequest req = new AddSignUpRequest(user.getEmail() + "Co", user.getName() + "Na", user.getNickName() + "NI",
                user.getLoginId() + "ID", user.getPassword() + "!@");

        //when
        ResultActions action = mvc.perform(post(URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 이메일 중복 으로 실패")
    void emailOverlapSignUpFail() throws Exception {
        //given
        User user = createUser("test_user_ids", "email@email.com");
        AddSignUpRequest req = new AddSignUpRequest(user.getEmail(), user.getName(), user.getNickName(),
                user.getLoginId() + "@", user.getPassword());

        //when
        ResultActions action = mvc.perform(post(URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().is(ErrorCode.EMAIL_ALREADY_PRESENT.getStatus()));
        action.andExpect(result -> result.getResolvedException().getMessage().equals(ErrorCode.EMAIL_ALREADY_PRESENT));
    }

    private User createUser(String id, String email) {
        Information userInfo = new Information(email, "test_name", "test_Nickname");
        return userRepository.save(new User(id, "password", userInfo));
    }
}
