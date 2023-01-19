package github.oineh.monitoring.user.web;

import github.oineh.monitoring.user.service.SignUpService;
import github.oineh.monitoring.user.web.req.SignUpReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class ApiUserController {

    private final SignUpService signUpService;

    @PostMapping("/singup")
    public void postSingUp(@RequestBody SignUpReq signUpReq) {
        signUpService.signUp(signUpReq);
    }

}
