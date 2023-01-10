package github.oineh.monitoring.domain.pc;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.controller.pc.req.PcAddReq;
import github.oineh.monitoring.domain.connect.Connect;
import github.oineh.monitoring.domain.user.User;
import github.oineh.monitoring.domain.user.UserRepository;
import io.github.sno.network.Host;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PcService {

    private final UserRepository userRepository;

    @Transactional
    public void addUserPc(PcAddReq req, Host host, String loginId) {
        User user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
        user.updatePc(new Pc(req.getPcName(), req.getType()));
        user.getPc().updateConnect(Connect.icmp(user.getInformation().getNickName(), host.toString()));
    }

}
