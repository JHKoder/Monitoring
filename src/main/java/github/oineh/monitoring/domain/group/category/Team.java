package github.oineh.monitoring.domain.group.category;

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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User createUser;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> member = new ArrayList<>();
    private String name;

    public Team(User createUser, String name) {
        this.createUser = createUser;
        this.name = name;
    }

    public void updateMember(User user) {
        this.member.add(user);
    }

}
