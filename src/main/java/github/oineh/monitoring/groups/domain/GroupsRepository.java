package github.oineh.monitoring.groups.domain;

import github.oineh.monitoring.user.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Groups, Long> {

    Optional<List<Groups>> findByMemberUsers(User memberUser);
}
