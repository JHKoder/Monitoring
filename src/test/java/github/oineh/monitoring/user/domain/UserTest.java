package github.oineh.monitoring.user.domain;

import github.oineh.monitoring.user.domain.User.Information;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("유저 entity 테스트")
public class UserTest {

    @Test
    @DisplayName("유저 생성")
    public void create() {
        //given
        String id = "loginId";
        String pw = "password";
        String email = "kang@naver.com";
        String name = "강정훈";
        String nickName = "학생_1";
        Information information = new Information(email, name, nickName);

        //when
        User user = new User(id, pw, information);

        //then
        assertThat(user).isNotNull();
    }

    @Test
    @DisplayName("유저 생성에서 공백이나 널을 줄수 있다.")
    public void create_empty() {
        //given
        String id = "loginId";
        String pw = "password";
        String email = "kang@naver.com";
        String name = "강정훈";
        String nickName = null;
        Information information = new Information(email, name, nickName);

        //when
        User user = new User(id, pw, information);

        //then
        assertThat(user).isNotNull();
    }

}
