package github.oineh.monitoring.pc.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.pc.web.req.AddHostRequest;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import io.github.tcp.network.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PcService {

    private final UserRepository userRepository;

    @Transactional
    public void createPcHost(AddHostRequest req, Host host, String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));

        user.updatePc(req.getPcName(), req.getType());
        user.updateConnect(user.getNickName(), host.toString());
    }
}
