package github.oineh.monitoring.controller.team.res;

import github.oineh.monitoring.domain.connect.Connect;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamInDomainRes {

    private String name;
    private Long id;

    public TeamInDomainRes(Connect connect) {
        this.name = connect.getName();
        this.id = connect.getId();
    }
}
