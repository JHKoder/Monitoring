package github.oineh.monitoring.team.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.team.web.req.TeamCreateReq;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;

    @Transactional
    public void createTeam(TeamCreateReq req, String userId) {
        findGroups(req.getGroupsId()).getDepts().stream()
                .filter(dep -> dep.isId(req.getDeptId()))
                .findFirst()
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_GROUPS_DEPT))
                .updateTeam(teamRepository.save(new Team(findUser(userId), req.getName())));
    }

    private User findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private Groups findGroups(Long groupId) {
        return groupsRepository.findById(groupId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));
    }
}
