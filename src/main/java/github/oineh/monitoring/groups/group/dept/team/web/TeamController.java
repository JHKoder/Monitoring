package github.oineh.monitoring.groups.group.dept.team.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
public class TeamController {

    @GetMapping("/groups/{groupId}/teams/{teamId}")
    public String get() {
        return "team";
    }
}
