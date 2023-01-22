package github.oineh.monitoring.pc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.pc.domain.Type;
import github.oineh.monitoring.pc.web.req.PcAddReq;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static github.oineh.monitoring.user.domain.User.Information;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api PC 테스트")
public class ApiPcControllerTest extends IntegrationTest {


    static final String TARGET_RESOURCE = "/api/pc";
    User user;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        Information userInfo = new Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));
    }

    @Test
    @DisplayName("팀 초대 수락 테스트")
    void acceptTeamInvite() throws Exception {
        //given
        PcAddReq req = new PcAddReq("pc", Type.PC);

        //when
        ResultActions action = mvc.perform(post(TARGET_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }


}
