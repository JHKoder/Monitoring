package github.oineh.monitoring.user.service;

import github.oineh.monitoring.authority.domain.Auth;
import github.oineh.monitoring.authority.domain.AuthRepository;
import github.oineh.monitoring.authority.domain.Grade;
import github.oineh.monitoring.config.auth.UserLogin;
import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import github.oineh.monitoring.user.web.req.SignUpReq;
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
public class SignUpService implements UserDetailsService {

    private final UserRepository userRepository;

    private final AuthRepository authRepository;

    @Transactional
    public void signUp(SignUpReq req) {
        validateAlreadyLoginIdExisted(req.getLoginId());
        validateAlreadyEmailRegistered(req.getEmail());

        authRepository.save(new Auth(req.toUser(), Grade.USER));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserId(username);
        //TODO: 커스텀 예외
        Auth auth = authRepository.findByUser(user).orElseThrow();

        log.info("loadUserByUsername  : " + user.getId() + " Auth:" + auth.getGrades().toString());
        return UserLogin.of(user, auth);
    }

    private void validateAlreadyEmailRegistered(String email) {
        if (userRepository.findByInformationEmail(email).isPresent()) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_PRESENT);
        }
    }

    private void validateAlreadyLoginIdExisted(String loginId) {
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new ApiException(ErrorCode.USERS_ALREADY_PRESENT);
        }
    }

    private User findByUserId(String userId) {
        return userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
