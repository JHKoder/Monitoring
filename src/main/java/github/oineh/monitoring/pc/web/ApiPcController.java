package github.oineh.monitoring.pc.web;

import github.oineh.monitoring.pc.service.PcClientIpUtils;
import github.oineh.monitoring.pc.service.PcService;
import github.oineh.monitoring.pc.web.req.PcAddReq;
import io.github.tcp.network.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequestMapping("/api/pc")
@RequiredArgsConstructor
public class ApiPcController {

    private final PcService pcService;


    @PostMapping
    public void create(PcAddReq req, HttpServletRequest request, Principal principal) {
        Host host = PcClientIpUtils.filterIp(request);
        System.out.println("=========");
        System.out.println("=========");
        System.out.println("=========");
        System.out.println("" + host.toString());
        pcService.addUserPc(req, host, principal.getName());
    }
}
