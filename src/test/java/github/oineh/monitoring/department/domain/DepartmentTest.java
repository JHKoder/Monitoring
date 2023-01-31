package github.oineh.monitoring.department.domain;

import fixture.UserFixture;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("부서")
public class DepartmentTest {

    User user;
    Groups groups;
    Department department;


    @BeforeEach
    void setup() {
        user = UserFixture.getUser();
        groups = new Groups(user, "groupsName");
        department = new Department(user, "부서");
    }

    @Test
    @DisplayName("만들기")
    void deptCreate() {
        //given
        Department department1 = new Department(user, "부서");

        //when
        groups.updateDept(department1);

        //then
        assertThat(groups.getDepts()).contains(department1);
    }
}
