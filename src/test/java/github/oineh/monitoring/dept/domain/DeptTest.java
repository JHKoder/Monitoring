package github.oineh.monitoring.dept.domain;

import fixture.UserFixture;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("부서")
public class DeptTest {

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
    @DisplayName("만들기")
    void deptCreate() {
        //given
        Dept dept = new Dept(user, "부서");

        //when
        groups.updateDept(dept);

        //then
        assertThat(groups.getDepts()).contains(dept);
    }
}
