package github.oineh.monitoring.user.service;

import github.oineh.monitoring.auth.domain.Auth;
import github.oineh.monitoring.auth.domain.AuthRepository;
import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.team.domain.TeamRepository;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import github.oineh.monitoring.user.web.req.SignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final GroupsRepository groupsRepository;
    private final TeamRepository teamRepository;


    @Transactional
    public void signUp(SignUpReq req) {
        validateAlreadyLoginIdExisted(req.getLoginId());
        validateAlreadyEmailRegistered(req.getEmail());

        User user = userRepository.save(req.toUser());
        Auth auth = authRepository.save(Auth.ofUser(user));
        System.out.println(auth.getUser() + "," + auth.getGrades().toString());
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
