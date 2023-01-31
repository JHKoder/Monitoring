package github.oineh.monitoring.pc.web;

import github.oineh.monitoring.pc.service.PcService;
import github.oineh.monitoring.pc.web.req.AddPcRequest;
import io.github.tcp.network.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequestMapping("/api/pc")
@RequiredArgsConstructor
public class PcController {

    private final PcService pcService;
    private final CreateHostService createHostService;


    @PostMapping
    public void create(@RequestBody AddPcRequest req, HttpServletRequest request, Principal principal) {
        Host host = createHostService.createHost(request);
        pcService.createPcHost(req, host, principal.getName());
    }
}
