package github.oineh.monitoring.domain.belong;

import static org.assertj.core.api.Assertions.assertThat;

import fixture.UserFixture;
import github.oineh.monitoring.domain.belong.group.Department;
import github.oineh.monitoring.domain.belong.group.Division;
import github.oineh.monitoring.domain.belong.group.Team;
import github.oineh.monitoring.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("소속 entity 테스트")
public class GroupsTest {


    User userToBelongAdminUser;

    Division division;
    Department department;
    Team team;


    @BeforeEach
    void setup() {
        userToBelongAdminUser = UserFixture.getUserEmpty();

        division = new Division(userToBelongAdminUser, "대원초등학교");
        department = new Department(userToBelongAdminUser, "1학년");
        division.updateDepartment(department);
        team = new Team(userToBelongAdminUser, "1반");
        department.updateTeam(team);
    }

    @Test
    @DisplayName("단체 소속 생성")
    public void create() {
        //given - when
        Groups groups = new Groups(division, department, team);

        //then
        assertThat(groups.getDivision()).isEqualTo(division);
        assertThat(groups.getDivision().getDepartment()).contains(department);
        assertThat(groups.getDivision().getDepartment().get(0).getTeams()).contains(team);
    }

    @Test
    @DisplayName("상급 소속 생성")
    public void top_team_create() {
        //given
        String belongLargeName = "대원초등학교";

        //when
        Division division = new Division(userToBelongAdminUser, belongLargeName);

        //then
        assertThat(division.getName()).isEqualTo(belongLargeName);
    }

    @Test
    @DisplayName("중급 소속 생성")
    public void mid_team_create() {
        //given
        String mediumName = "3학년";
        Department department = new Department(userToBelongAdminUser, mediumName);

        //when
        division.updateDepartment(department);

        //then
        assertThat(division.getDepartment()).contains(department);
    }

    @Test
    @DisplayName("하급 소속 생성")
    public void button_team_create() {
        //given
        String belongSmallName = "3반";
        Team team = new Team(userToBelongAdminUser, belongSmallName);

        //when
        department.updateTeam(team);

        //then
        assertThat(department.getTeams()).contains(team);
    }
}
