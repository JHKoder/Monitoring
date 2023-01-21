package github.oineh.monitoring.groups.domain;

import fixture.UserFixture;
import github.oineh.monitoring.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("그룹 entity 테스트")
public class GroupsTest {

    User user = UserFixture.getUser();


    @Test
    @DisplayName("그룹 생성")
    public void create() {
        //given
        String name = "성남그룹";

        //when
        Groups groups = new Groups(user, name);

        //then
        assertThat(groups.getName()).isEqualTo(name);
    }
}
