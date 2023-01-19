package github.oineh.monitoring.groups.domain;


import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.groups.group.dept.domain.Dept;
import github.oineh.monitoring.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> memberUsers = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Dept> depts = new ArrayList<>();


    public Groups(User adminUser, String name) {
        this.adminUser = adminUser;
        this.memberUsers.add(adminUser);
        this.name = name;
    }

    public boolean checkMember(User user) {
        return memberUsers.contains(user);
    }

    public Groups updateMember(User user) {
        this.memberUsers.add(user);

        return this;
    }

    public void updateDept(Dept dept) {
        this.depts.add(dept);
    }
}
