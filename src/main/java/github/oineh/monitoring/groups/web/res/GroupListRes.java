package github.oineh.monitoring.groups.web.res;

import github.oineh.monitoring.groups.group.dept.domain.Dept;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupListRes {

    private List<GroupInDept> group = new ArrayList<>();
    private Long groupsId;

    private GroupListRes(Long groupsId, List<GroupInDept> group) {
        this.groupsId = groupsId;
        this.group = group;
    }

    public static GroupListRes of(Long groupsId, List<Dept> groups) {
        return new GroupListRes(groupsId, groups.stream()
            .map(dept -> new GroupInDept(dept.getName(), dept.getId(),
                GroupInDeptInTeam.of(dept.getTeams()))
            ).collect(Collectors.toList()));
    }

    @Getter
    @NoArgsConstructor
    private static class GroupInDept {

        private String name;
        private Long deptId;
        private List<GroupInDeptInTeam> list;

        public GroupInDept(String name, Long deptId, List<GroupInDeptInTeam> list) {
            this.name = name;
            this.deptId = deptId;
            this.list = list;
        }
    }

    @Getter
    @NoArgsConstructor
    private static class GroupInDeptInTeam {

        private String name;
        private Long teamId;

        public GroupInDeptInTeam(String name, Long teamId) {
            this.name = name;
            this.teamId = teamId;
        }

        public static List<GroupInDeptInTeam> of(List<Team> teams) {
            return teams.stream()
                .map(team -> new GroupInDeptInTeam(team.getName(), team.getId()))
                .collect(Collectors.toList());
        }
    }

}
