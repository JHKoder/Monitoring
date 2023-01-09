package github.oineh.monitoring.controller.group.res;

import github.oineh.monitoring.domain.group.category.Dept;
import github.oineh.monitoring.domain.group.category.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupListRes {

    private List<GroupInDept> group = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    private static class GroupInDept {

        private String name;
        private Long id;
        private List<GroupInDeptInTeam> list;

        public GroupInDept(String name, Long id, List<GroupInDeptInTeam> list) {
            this.name = name;
            this.id = id;
            this.list = list;
        }
    }

    @Getter
    @NoArgsConstructor
    private static class GroupInDeptInTeam {

        private String name;
        private Long id;

        public GroupInDeptInTeam(String name, Long id) {
            this.name = name;
            this.id = id;
        }

        public static List<GroupInDeptInTeam> of(List<Team> teams) {
            return teams.stream()
                .map(team -> new GroupInDeptInTeam(team.getName(), team.getId()))
                .collect(Collectors.toList());
        }
    }

    public static GroupListRes of(List<Dept> groups) {
        return new GroupListRes(groups.stream()
            .map(dept -> new GroupInDept(dept.getName(), dept.getId(),
                GroupInDeptInTeam.of(dept.getTeams()))
            ).collect(Collectors.toList()));
    }

    private GroupListRes(List<GroupInDept> group) {
        this.group = group;
    }
}
