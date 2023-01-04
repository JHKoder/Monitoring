package github.oineh.monitoring.domain.user.req;


import lombok.Data;

@Data
public class SingUpReq {

    private String email;
    private String name;
    private String nickName;
    private String loginId;
    private String password;
}
