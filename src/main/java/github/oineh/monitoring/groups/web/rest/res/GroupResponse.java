package github.oineh.monitoring.groups.web.rest.res;

import github.oineh.monitoring.groups.domain.Groups;
import lombok.Getter;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Getter
public class GroupResponse {

    private Long groupsId;
    private String name;

    public GroupResponse(Long groupsId, String name) {
        this.groupsId = groupsId;
        this.name = name;
    }

    public static List<GroupResponse> ofList(List<Groups> groups) {
        return groups.stream()
                .map(group -> new GroupResponse(group.getId(), group.getName()))
                .collect(toList());
    }
}
