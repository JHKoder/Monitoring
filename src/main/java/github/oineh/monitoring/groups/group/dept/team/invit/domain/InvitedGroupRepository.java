package github.oineh.monitoring.groups.group.dept.team.invit.domain;

import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.user.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedGroupRepository extends JpaRepository<InvitedTeam, Long> {

    Optional<List<InvitedTeam>> findByTargetUser(User targetUser);

    Optional<InvitedTeam> findByTargetUserAndTeam(User targetUser, Team team);
}
