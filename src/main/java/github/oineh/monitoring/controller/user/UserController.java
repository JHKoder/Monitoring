package github.oineh.monitoring.controller.user;

import github.oineh.monitoring.domain.user.UserService;
import github.oineh.monitoring.domain.user.req.SingUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/authorities")
    public String getPrincipalInfo(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping("/")
    public String get() {
        return "index";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/singup")
    public String getSingUp() {
        return "singup";
    }

    @GetMapping("/user")
    public String getUser() {
        return "user";
    }

    @PostMapping("/singup")
    public String postSingUp(SingUpReq singUpReq) {
        userService.singup(singUpReq);
        return "index";
    }

}
