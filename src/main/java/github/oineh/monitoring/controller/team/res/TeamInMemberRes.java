package github.oineh.monitoring.controller.team.res;

import github.oineh.monitoring.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamInMemberRes {

    private String name;
    private Long id;

    public TeamInMemberRes(User user) {
        this.name = user.getInformation().getName();
        this.id = user.getPc().getConnect().getId();
    }
}
