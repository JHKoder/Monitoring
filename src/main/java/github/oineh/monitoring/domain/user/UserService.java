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

        if (userRepository.findByLoginId(req.getLoginId()).isPresent()) {
            throw new ApiException(ErrorCode.USERS_ALREADY_PRESENT);
        }

        Information information = new User.Information(req.getEmail(), req.getName(), req.getNickName());
        User user = new User(req.getLoginId(), req.getPassword(), information);
        
        if (userRepository.findByInformationEmail(req.getEmail()).isPresent()) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_PRESENT);
        }
        userRepository.save(user);
        authRepository.save(new Auth(user, Grade.USER));
    }


    public User findByUserId(String userId) {
        return userRepository.findByLoginId(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_NICKNAME));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserId(username);
        Auth auth = authRepository.findByUser(user)
            .orElseThrow();
        log.info("loadUserByUsername  : " + user.getId() + " Auth:" + auth.getGrade().iterator().next().getName());
        return UserLogin.of(user, auth);
    }

}
