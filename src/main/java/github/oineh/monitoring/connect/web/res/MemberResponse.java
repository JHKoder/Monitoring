package github.oineh.monitoring.connect.web.res;

import github.oineh.monitoring.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {

    private String name;
    private Long id;

    public MemberResponse(User user) {
        this.name = user.getName();
        this.id = user.getConnectId();
    }
}
