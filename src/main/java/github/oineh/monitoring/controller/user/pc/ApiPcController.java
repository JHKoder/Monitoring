package github.oineh.monitoring.controller.user.pc;

import github.oineh.monitoring.controller.user.pc.req.PcAddReq;
import github.oineh.monitoring.domain.user.pc.PcClientIpUtils;
import github.oineh.monitoring.domain.user.pc.PcService;
import io.github.sno.network.Host;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/pc")
@RequiredArgsConstructor
public class ApiPcController {

    private final PcService pcService;


    @PostMapping
    public ResponseEntity<Void> create(PcAddReq req, HttpServletRequest request, Principal principal) {
        Host host = PcClientIpUtils.filterIp(request);

        pcService.addUserPc(req, host, principal.getName());

        return ResponseEntity.ok().build();
    }


}
