package github.oineh.monitoring.groups.group.dept.team.domain;

import fixture.UserFixture;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.group.dept.domain.Dept;
import github.oineh.monitoring.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("entity 팀")
public class TeamTest {

    User user;
    Groups groups;
    Dept dept;


    @BeforeEach
    void setup() {
        user = UserFixture.getUser();
        groups = new Groups(user, "groupsName");
        dept = new Dept(user, "부서");
    }

    @Test
    void teamCreate() {
        //given
        Team team = new Team(user, "개발팀");

        //when
        dept.updateTeam(team);

        //then
        assertThat(dept.getTeams()).contains(team);
    }
}
