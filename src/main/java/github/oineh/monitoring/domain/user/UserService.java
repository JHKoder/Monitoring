package github.oineh.monitoring.domain.user;

import github.oineh.monitoring.config.auth.UserLogin;
import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.controller.user.req.SingUpReq;
import github.oineh.monitoring.domain.authority.Auth;
import github.oineh.monitoring.domain.authority.AuthRepository;
import github.oineh.monitoring.domain.authority.Grade;
import github.oineh.monitoring.domain.user.User.Information;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AuthRepository authRepository;

    @Transactional
    public void singup(SingUpReq req) {
        checkUser(req.getLoginId());
        validateAlreadyEmailRegistered(req.getEmail());

        Information information = new User.Information(req.getEmail(), req.getName(), req.getNickName());
        User user = new User(req.getLoginId(), req.getPassword(), information);

        userRepository.save(user);
        authRepository.save(new Auth(user, Grade.USER));
    }

    private void validateAlreadyEmailRegistered(String email) {
        if (userRepository.findByInformationEmail(email).isPresent()) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_PRESENT);
        }
    }

    private void checkUser(String loginId) {
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new ApiException(ErrorCode.USERS_ALREADY_PRESENT);
        }
    }

    private User findByUserId(String userId, ErrorCode errorCode) {
        return userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(errorCode));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserId(username, ErrorCode.NOT_FOUND_USER);
        Auth auth = authRepository.findByUser(user)
            .orElseThrow();
        log.info("loadUserByUsername  : " + user.getId() + " Auth:" + auth.getGradeName());
        return UserLogin.of(user, auth);
    }

}
