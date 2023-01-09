package github.oineh.monitoring.domain.groups;


import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.domain.group.category.Dept;
import github.oineh.monitoring.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class Groups extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private User adminUser;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> memberUser = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Dept> dept = new ArrayList<>();

    public Groups(User adminUser, String name) {
        this.adminUser = adminUser;
        this.name = name;
    }

    public void updateMember(User user) {
        this.memberUser.add(user);
    }

    public void updateDept(Dept dept) {
        this.dept.add(dept);
    }
}
