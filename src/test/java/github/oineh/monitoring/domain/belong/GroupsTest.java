package github.oineh.monitoring.domain.belong;

import static org.assertj.core.api.Assertions.assertThat;

import fixture.UserFixture;
import github.oineh.monitoring.domain.group.category.Dept;
import github.oineh.monitoring.domain.group.category.Large;
import github.oineh.monitoring.domain.group.category.Team;
import github.oineh.monitoring.domain.groups.Groups;
import github.oineh.monitoring.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("소속 entity 테스트")
public class GroupsTest {


    User userToBelongAdminUser;

    Large large;
    Dept dept;
    Team team;


    @BeforeEach
    void setup() {
        userToBelongAdminUser = UserFixture.getUserEmpty();

        large = new Large(userToBelongAdminUser, "대원초등학교");
        dept = new Dept(userToBelongAdminUser, "1학년");
        large.updateDepartment(dept);
        team = new Team(userToBelongAdminUser, "1반");
        dept.updateTeam(team);
    }

    @Test
    @DisplayName("단체 소속 생성")
    public void create() {
        String name = "lol";
        //given - when
        Groups groups = new Groups(name, large, dept, team);

        //then
        assertThat(groups.getLarge()).isEqualTo(large);
        assertThat(groups.getLarge().getMedium()).contains(dept);
        assertThat(groups.getLarge().getMedium().get(0).getTeams()).contains(team);
    }

    @Test
    @DisplayName("상급 소속 생성")
    public void top_team_create() {
        //given
        String belongLargeName = "대원초등학교";

        //when
        Large large = new Large(userToBelongAdminUser, belongLargeName);

        //then
        assertThat(large.getName()).isEqualTo(belongLargeName);
    }

    @Test
    @DisplayName("중급 소속 생성")
    public void mid_team_create() {
        //given
        String mediumName = "3학년";
        Dept dept = new Dept(userToBelongAdminUser, mediumName);

        //when
        large.updateDepartment(dept);

        //then
        assertThat(large.getMedium()).contains(dept);
    }

    @Test
    @DisplayName("하급 소속 생성")
    public void button_team_create() {
        //given
        String belongSmallName = "3반";
        Team team = new Team(userToBelongAdminUser, belongSmallName);

        //when
        dept.updateTeam(team);

        //then
        assertThat(dept.getTeams()).contains(team);
    }
}
