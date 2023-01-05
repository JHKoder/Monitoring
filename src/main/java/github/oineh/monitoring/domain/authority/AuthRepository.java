package github.oineh.monitoring.domain.authority;

import github.oineh.monitoring.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByUser(User user);

}
