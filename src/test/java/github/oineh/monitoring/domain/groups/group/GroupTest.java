package github.oineh.monitoring.domain.groups.group;


import static org.assertj.core.api.Assertions.assertThat;

import fixture.UserFixture;
import github.oineh.monitoring.domain.groups.Groups;
import github.oineh.monitoring.domain.groups.group.category.Dept;
import github.oineh.monitoring.domain.groups.group.category.Team;
import github.oineh.monitoring.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("부서 & 팀 entity test")
public class GroupTest {

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
    void deptCreate() {
        //given
        Dept dept = new Dept(user, "부서");

        //when
        groups.updateDept(dept);

        //then
        assertThat(groups.getDepts()).contains(dept);
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
