package github.oineh.monitoring.connect.web.res;

import github.oineh.monitoring.connect.domain.Connect;
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
