package github.oineh.monitoring.domain.belong;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Department> department = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private User adminUser;


    private String name;

    public Division(User adminUser, String name) {
        this.adminUser = adminUser;
        this.name = name;
    }

    public void updateDepartment(Department department) {
        this.department.add(department);
    }
}
