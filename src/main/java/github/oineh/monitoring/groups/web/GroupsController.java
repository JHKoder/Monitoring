package github.oineh.monitoring.groups.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class GroupsController {

    @GetMapping("/groups")
    public String groups() {
        return "groups";
    }
}
