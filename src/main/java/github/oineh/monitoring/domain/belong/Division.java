package github.oineh.monitoring.domain.belong;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
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
        this.name=name;
    }

    public void updateDepartment(Department department) {
        this.department.add(department);
    }
}
