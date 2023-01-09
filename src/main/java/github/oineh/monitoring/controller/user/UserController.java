package github.oineh.monitoring.controller.user;

import github.oineh.monitoring.controller.user.req.SingUpReq;
import github.oineh.monitoring.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/singup")
    public String singup() {
        return "singup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @PostMapping("/singup")
    public String postSingUp(SingUpReq singUpReq) {
        userService.singup(singUpReq);
        return "index";
    }

}
