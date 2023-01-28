package github.oineh.monitoring.team.web;


import github.oineh.monitoring.team.service.AddressService;
import github.oineh.monitoring.team.web.req.TeamCreateIpReq;
import github.oineh.monitoring.team.web.req.TeamCreatePortReq;
import github.oineh.monitoring.team.web.req.TeamCreateUrlReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team/address")
@RequiredArgsConstructor
public class ApiTeamAddressController {

    private final AddressService addressService;


    @PostMapping("/url")
    public void createUrl(@RequestBody TeamCreateUrlReq req) {
        addressService.createUrl(req);
    }

    @PostMapping("/ip-port")
    public void createPort(@RequestBody TeamCreatePortReq req) {
        addressService.createIpPort(req);
    }

    @PostMapping("/ip")
    public void createIp(@RequestBody TeamCreateIpReq req) {
        addressService.createIp(req);
    }
}
