package github.oineh.monitoring.team.web.rest;

import github.oineh.monitoring.team.service.TeamService;
import github.oineh.monitoring.team.web.rest.req.TeamCreateReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public void createGroupTeam(@RequestBody TeamCreateReq req, Principal principal) {
        teamService.createTeam(req, principal.getName());
    }
}
