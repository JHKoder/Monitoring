package github.oineh.monitoring.controller.socket;


import github.oineh.monitoring.controller.pc.req.ByPcReq;
import github.oineh.monitoring.controller.pc.res.ConnectStatusRes;
import github.oineh.monitoring.domain.connect.ConnectService;
import github.oineh.monitoring.domain.pc.PcClientIpUtils;
import io.github.sno.network.Host;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/net")
@RequiredArgsConstructor
public class ApiSocketController {

    private final ConnectService connectService;

    @PostMapping("/connects")
    public ResponseEntity<ConnectStatusRes> isPcStatus(ByPcReq byPcReq, HttpServletRequest request) {
        Host host = PcClientIpUtils.filterIp(request);

        ConnectStatusRes res = connectService.findConnectStatus(byPcReq, host);

        return ResponseEntity.ok(res);
    }
}
