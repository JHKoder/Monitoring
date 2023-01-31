package github.oineh.monitoring.pc.web;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import io.github.tcp.network.Host;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class CreateHostService {

    public Host createHost(HttpServletRequest request) {
        try {
            log.info("접근 IP:" + request.getRemoteAddr());
            return Host.from(request.getRemoteAddr());
        } catch (Exception e) {
            throw new ApiException(ErrorCode.INTERNAL_CLIENT_IP6V_NO);
        }
    }
}

