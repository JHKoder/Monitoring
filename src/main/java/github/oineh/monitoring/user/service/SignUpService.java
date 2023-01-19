package github.oineh.monitoring.user.service;

import github.oineh.monitoring.authority.domain.Auth;
import github.oineh.monitoring.authority.domain.AuthRepository;
import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.user.domain.UserRepository;
import github.oineh.monitoring.user.web.req.SignUpReq;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;

    private final AuthRepository authRepository;

    @Transactional
    public void signUp(SignUpReq req) {
        validateAlreadyLoginIdExisted(req.getLoginId());
        validateAlreadyEmailRegistered(req.getEmail());

        authRepository.save(Auth.ofUser(req.toUser()));
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
}
