package github.oineh.monitoring.groups.group.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class GroupController {

    @GetMapping("/groups/{groupId}")
    public String findGroupPage() {
        return "group";
    }
}
