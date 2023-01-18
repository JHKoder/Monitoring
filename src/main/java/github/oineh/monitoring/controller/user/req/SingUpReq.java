package github.oineh.monitoring.controller.user.req;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SingUpReq {

    private String email;
    private String name;
    private String nickName;
    private String loginId;
    private String password;
}
