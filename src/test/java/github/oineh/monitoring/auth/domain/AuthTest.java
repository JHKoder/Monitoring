package github.oineh.monitoring.auth.domain;

import fixture.UserFixture;
import github.oineh.monitoring.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("권한")
public class AuthTest {

    @Test
    @DisplayName("부여")
    void create() {
        //given
        User user = UserFixture.getUser();
        Grade grade = Grade.USER;

        //when
        Auth auth = new Auth(user, grade);

        //then
        assertThat(auth).isNotNull();
    }
}
