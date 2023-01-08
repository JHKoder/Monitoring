package github.oineh.monitoring.domain.groups.invit;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedGroupsRepository extends JpaRepository<InvitedGroups, Long> {

    Optional<List<InvitedGroups>> findByTargetUser(Long id);

    Optional<InvitedGroups> findByIdAndTargetUser(String invite, Long id);
}
