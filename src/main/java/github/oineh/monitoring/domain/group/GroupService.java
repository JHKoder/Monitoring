package github.oineh.monitoring.domain.group;

import github.oineh.monitoring.controller.group.res.GroupListRes;
import github.oineh.monitoring.domain.group.category.LargeRepository;
import github.oineh.monitoring.domain.group.category.MediumRepository;
import github.oineh.monitoring.domain.group.category.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final LargeRepository largeRepository;
    private final MediumRepository mediumRepository;
    private final TeamRepository teamRepository;

    public GroupListRes findGroup(Long groupId, String userId) {
        return null;
    }
}
