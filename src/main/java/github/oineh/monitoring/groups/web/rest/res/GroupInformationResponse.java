package github.oineh.monitoring.groups.web.rest.res;

import github.oineh.monitoring.department.domain.Department;
import github.oineh.monitoring.team.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
public class GroupInformationResponse {

    private List<GroupInDepartment> group = new ArrayList<>();
    private Long groupsId;


    private GroupInformationResponse(Long groupsId, List<GroupInDepartment> group) {
        this.groupsId = groupsId;
        this.group = group;
    }

    public static GroupInformationResponse of(Long groupsId, List<Department> groups) {
        return new GroupInformationResponse(groupsId, groups.stream()
                .map(department -> new GroupInDepartment(department.getName(), department.getId(), GroupInDeptInTeam.of(department.getTeams())))
                .collect(toList()));
    }

    @Getter
    @NoArgsConstructor
    private static class GroupInDepartment {

        private String name;
        private Long deptId;
        private List<GroupInDeptInTeam> list;

        public GroupInDepartment(String name, Long deptId, List<GroupInDeptInTeam> list) {
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
                    .collect(toList());
        }
    }
}
