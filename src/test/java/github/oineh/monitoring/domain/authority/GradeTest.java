package github.oineh.monitoring.domain.authority;

import static org.assertj.core.api.Assertions.assertThat;

import fixture.UserFixture;
import github.oineh.monitoring.domain.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("권한 entity 테스트")
public class GradeTest {


    @Test
    @DisplayName("레벨 생성")
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
