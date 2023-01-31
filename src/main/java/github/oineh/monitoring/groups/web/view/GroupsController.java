package github.oineh.monitoring.groups.web.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("viewGroups")
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupsController {

    @GetMapping
    public String groups() {
        return "groups";
    }

    @GetMapping("/{groupId}")
    public String detail() {
        return "group";
    }
}
