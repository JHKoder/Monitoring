package github.oineh.monitoring.domain.groups.invit;

import github.oineh.monitoring.domain.groups.Groups;
import github.oineh.monitoring.domain.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedGroupsRepository extends JpaRepository<InvitedGroups, Long> {

    Optional<List<InvitedGroups>> findByTargetUser(User targetUser);

    Optional<InvitedGroups> findByTargetUserAndGroups(User targetUser, Groups groups);
}
