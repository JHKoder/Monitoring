package github.oineh.monitoring.connect.web;

import github.oineh.monitoring.connect.service.ConnectService;
import github.oineh.monitoring.connect.web.res.AddressPingResponse;
import github.oineh.monitoring.connect.web.res.AddressResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/address/teams")
@RequiredArgsConstructor
public class AddressController {

    private final ConnectService connectService;


    @GetMapping("/{teamId}")
    public List<AddressResponse> findAddress(@PathVariable("teamId") Long teamId) {
        return connectService.findAddress(teamId);
    }

    @GetMapping("/{teamId}/connects/{connectId}")
    public AddressPingResponse connectionAddress(@PathVariable("teamId") Long teamId, @PathVariable Long connectId) {
        return connectService.findAddressPing(teamId, connectId);
    }


}
