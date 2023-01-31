package github.oineh.monitoring.user.web.rest;

import github.oineh.monitoring.user.service.SignUpService;
import github.oineh.monitoring.user.web.rest.req.AddSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public void makeUser(@RequestBody AddSignUpRequest addSignUpRequest) {
        signUpService.signUp(addSignUpRequest);
    }
}
