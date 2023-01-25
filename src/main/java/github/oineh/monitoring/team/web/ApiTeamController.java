package github.oineh.monitoring.team.web;

import github.oineh.monitoring.team.service.TeamService;
import github.oineh.monitoring.team.web.req.TeamCreateReq;

import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class ApiTeamController {

    private final TeamService teamService;


    @PostMapping
    public void createGroupTeam(@RequestBody TeamCreateReq req, Principal principal) {
        teamService.createTeam(req, principal.getName());
    }
}
