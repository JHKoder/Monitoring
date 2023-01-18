package github.oineh.monitoring.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
@RequiredArgsConstructor
public class UserController {

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

    @GetMapping("/mypage")
    public String myPage() {
        return "mypage";
    }
}
