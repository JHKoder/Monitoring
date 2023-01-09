package github.oineh.monitoring.controller.team;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeamController {

    @GetMapping("/team/room/{teamId}")
    public String get() {
        return "team";
    }
}
