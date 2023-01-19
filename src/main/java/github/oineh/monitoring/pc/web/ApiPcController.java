package github.oineh.monitoring.pc.web;

import github.oineh.monitoring.pc.service.PcClientIpUtils;
import github.oineh.monitoring.pc.service.PcService;
import github.oineh.monitoring.pc.web.req.PcAddReq;
import io.github.tcp.network.Host;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/pc")
@RequiredArgsConstructor
public class ApiPcController {

    private final PcService pcService;

    @PostMapping
    public void create(PcAddReq req, HttpServletRequest request, Principal principal) {
        Host host = PcClientIpUtils.filterIp(request);
        pcService.addUserPc(req, host, principal.getName());
    }

}
