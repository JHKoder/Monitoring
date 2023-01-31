package github.oineh.monitoring.user.web.rest.req;


import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddSignUpRequest {

    private String email;
    private String name;
    private String nickName;
    private String loginId;
    private String password;


    public User toUser() {
        return new User(loginId, password, toInformation());
    }

    private Information toInformation() {
        return new Information(email, name, nickName);
    }
}
