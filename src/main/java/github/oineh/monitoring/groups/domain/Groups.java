package github.oineh.monitoring.groups.domain;


import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.department.domain.Department;
import github.oineh.monitoring.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;


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

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> memberUsers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Department> depts = new ArrayList<>();


    public Groups(User adminUser, String name) {
        this.adminUser = adminUser;
        this.memberUsers.add(adminUser);
        this.name = name;
    }

    public boolean containsUser(User user) {
        return memberUsers.contains(user);
    }

    public Groups updateMember(User user) {
        this.memberUsers.add(user);

        return this;
    }

    public void updateDept(Department dept) {
        this.depts.add(dept);
    }
}
