package github.oineh.monitoring.groups.web.res;

import github.oineh.monitoring.groups.domain.Groups;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class GrouopsRes {

    private Long groupsId;
    private String name;


    public GrouopsRes(Long groupsId, String name) {
        this.groupsId = groupsId;
        this.name = name;
    }

    public List<GrouopsRes> ofList(List<Groups> groups) {
        return groups.stream()
                .map(group -> new GrouopsRes(group.getId(), group.getName()))
                .collect(Collectors.toList());
    }
}
