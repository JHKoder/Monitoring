package github.oineh.monitoring.team.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("viewTeam")
@RequestMapping
public class TeamController {

    @GetMapping("/groups/{groupId}/teams/{teamId}")
    public String team() {
        return "team";
    }
}
