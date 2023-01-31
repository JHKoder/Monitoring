package github.oineh.monitoring.invit.domain;

import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.user.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedGroupsRepository extends JpaRepository<InvitedGroups, Long> {

    Optional<List<InvitedGroups>> findByTargetUser(User targetUser);

    Optional<InvitedGroups> findByTargetUserAndGroups(User targetUser, Groups groups);
}
