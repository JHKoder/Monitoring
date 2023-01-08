package github.oineh.monitoring.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String username);

    Optional<User> findByInformationEmail(String email);
}
