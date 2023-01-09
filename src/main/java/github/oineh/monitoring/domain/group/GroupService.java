package github.oineh.monitoring.domain.group;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.controller.group.req.GroupCreateTeamDeptReq;
import github.oineh.monitoring.controller.group.req.GroupCreateTeamReq;
import github.oineh.monitoring.controller.group.res.GroupListRes;
import github.oineh.monitoring.domain.group.category.Dept;
import github.oineh.monitoring.domain.group.category.DeptRepository;
import github.oineh.monitoring.domain.group.category.Team;
import github.oineh.monitoring.domain.group.category.TeamRepository;
import github.oineh.monitoring.domain.groups.Groups;
import github.oineh.monitoring.domain.groups.GroupsRepository;
import github.oineh.monitoring.domain.user.User;
import github.oineh.monitoring.domain.user.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final DeptRepository deptRepository;
    private final TeamRepository teamRepository;

    private final UserRepository userRepository;
    private final GroupsRepository groupsRepository;

    // 그룹네 모든 소속 & 팀 리턴
    @Transactional
    public GroupListRes findGroup(Long groupId, String userId) {
        User user = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        Groups groups = groupsRepository.findById(groupId)
            .orElse(null);
        List<Long> deptIds = groups.getDept().stream().map(Dept::getId).collect(Collectors.toList());
        List<Dept> depts = deptRepository.findAllById(deptIds);
        checkGroupInUser(user, groups);

        return GroupListRes.of(groupId, depts);
    }

    @Transactional
    public void createGroup(GroupCreateTeamDeptReq req, String userId) {

        if (req.getName() == null) {
            throw new IllegalArgumentException("아니");
        }
        Groups groups = groupsRepository.findById(req.getGroupsId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        User user = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        checkGroupInUser(user, groups);

        groups.updateDept(new Dept(user, req.getName()));
    }


    @Transactional
    public void createGroup(GroupCreateTeamReq req, String userId) {
        Groups groups = groupsRepository.findById(req.getGroupsId())
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_GROUPS));

        User user = userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        checkGroupInUser(user, groups);

        Dept dept = groups.getDept().stream()
            .filter(dep -> dep.getId().equals(req.getDeptId()))
            .findFirst()
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_GROUPS_DEPT));

        Team team = new Team(user, req.getName());

        dept.updateTeam(team);
    }

    private void checkGroupInUser(User user, Groups groups) {
        if (!user.getGroups().contains(groups)) {
            throw new ApiException(ErrorCode.SELECT_GROUP_USER);
        }
    }
}
