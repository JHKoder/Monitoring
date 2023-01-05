package github.oineh.monitoring.domain.user.req;

import lombok.Data;

@Data
public class UserLoginReq {

    private String username;
    private String password;
}
