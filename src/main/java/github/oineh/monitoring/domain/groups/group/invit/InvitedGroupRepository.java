package github.oineh.monitoring.domain.groups.group.invit;

import github.oineh.monitoring.domain.groups.group.category.Team;
import github.oineh.monitoring.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedGroupRepository extends JpaRepository<InvitedGroup, Long> {

    Optional<List<InvitedGroup>> findByTargetUser(User targetUser);

    Optional<InvitedGroup> findByTargetUserAndTeam(User targetUser, Team team);

}
