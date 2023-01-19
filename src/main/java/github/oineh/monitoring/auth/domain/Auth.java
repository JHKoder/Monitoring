package github.oineh.monitoring.auth.domain;

import github.oineh.monitoring.common.entity.BaseEntity;
import github.oineh.monitoring.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Auth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;
    @ElementCollection
    private Set<Grade> grades = new HashSet<>();

    public Auth(User user, Grade grade) {
        this.user = user;
        this.grades.add(grade);
    }

    public static Auth ofUser(User user) {
        return new Auth(user, Grade.USER);
    }
}
