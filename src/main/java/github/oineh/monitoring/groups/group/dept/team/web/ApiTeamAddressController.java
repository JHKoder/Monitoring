package github.oineh.monitoring.groups.group.dept.team.web;


import github.oineh.monitoring.connect.service.ConnectService;
import github.oineh.monitoring.groups.group.dept.team.web.req.TeamCreateIpReq;
import github.oineh.monitoring.groups.group.dept.team.web.req.TeamCreatePortReq;
import github.oineh.monitoring.groups.group.dept.team.web.req.TeamCreateUrlReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team/address")
@RequiredArgsConstructor
public class ApiTeamAddressController {

    private final ConnectService connectService;


    @PostMapping("/url")
    public void createUrl(@RequestBody TeamCreateUrlReq req) {
        connectService.createUrl(req);
    }

    @PostMapping("/ip-port")
    public void createPort(@RequestBody TeamCreatePortReq req) {
        connectService.createIpPort(req);
    }

    @PostMapping("/ip")
    public void createIp(@RequestBody TeamCreateIpReq req) {
        connectService.createIp(req);
    }
}
