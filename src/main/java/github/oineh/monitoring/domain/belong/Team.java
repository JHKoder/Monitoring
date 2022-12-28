package github.oineh.monitoring.domain.belong;

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

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private User adminUser;

    private String name;

    public Team(User adminUser, String name) {
        this.adminUser = adminUser;
        this.name=name;
    }

}
