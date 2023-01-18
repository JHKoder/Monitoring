package github.oineh.monitoring.groups.web.res;

import github.oineh.monitoring.groups.domain.Groups;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GroupsFindRes {

    private Long groupsId;
    private String name;

    public GroupsFindRes(Long groupsId, String name) {
        this.groupsId = groupsId;
        this.name = name;
    }

    public List<GroupsFindRes> ofList(List<Groups> groups) {
        return groups.stream()
            .map(group -> new GroupsFindRes(group.getId(), group.getName()))
            .collect(Collectors.toList());
    }

}
