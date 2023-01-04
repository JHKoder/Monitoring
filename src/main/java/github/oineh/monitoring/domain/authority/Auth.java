package github.oineh.monitoring.domain.authority;


import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.user.User;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @ElementCollection
    private Set<Grade> grade = new HashSet<>();

    public Auth(User user, Grade grade) {
        this.user = user;
        this.grade.add(grade);
    }

}
