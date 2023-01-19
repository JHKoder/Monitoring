package github.oineh.monitoring.user.service;

import github.oineh.monitoring.authority.domain.Auth;
import github.oineh.monitoring.authority.domain.AuthRepository;
import github.oineh.monitoring.config.auth.UserLogin;
import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.AuthenticationCustomException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
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
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUser(username);
        Auth auth = findAuth(user);

        log.info("loadUserByUsername  : " + user.getId() + " Auth:" + auth.getGrades().toString());
        return UserLogin.of(user, auth);
    }

    private Auth findAuth(User user) {
        return authRepository.findByUser(user)
            .orElseThrow(() -> new AuthenticationCustomException(ErrorCode.VALIDATE_AUTHENTICATION));
    }

    private User findUser(String userId) {
        return userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
