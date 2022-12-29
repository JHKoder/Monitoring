package github.oineh.monitoring.domain.authority;

import static lombok.AccessLevel.PROTECTED;

import github.oineh.monitoring.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    @Enumerated(EnumType.STRING)
    private GradeLevel level;

    public Grade(User user, GradeLevel level) {
        this.user = user;
        this.level = level;
    }
}
