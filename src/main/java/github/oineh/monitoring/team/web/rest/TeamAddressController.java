package github.oineh.monitoring.team.web.rest;


import github.oineh.monitoring.team.service.AddressService;
import github.oineh.monitoring.team.web.rest.req.AddressCreateIpRequest;
import github.oineh.monitoring.team.web.rest.req.AddressCreatePortRequest;
import github.oineh.monitoring.team.web.rest.req.AddressCreateUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team/address")
@RequiredArgsConstructor
public class TeamAddressController {

    private final AddressService addressService;

    @PostMapping("/url")
    public void createUrl(@RequestBody AddressCreateUrlRequest req) {
        addressService.createUrl(req);
    }

    @PostMapping("/port")
    public void createPort(@RequestBody AddressCreatePortRequest req) {
        addressService.createIpPort(req);
    }

    @PostMapping("/ip")
    public void createIp(@RequestBody AddressCreateIpRequest req) {
        addressService.createIp(req);
    }
}
