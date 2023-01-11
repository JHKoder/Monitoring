package github.oineh.monitoring.controller.groups;

import github.oineh.monitoring.controller.group.req.GroupInviteReq;
import github.oineh.monitoring.controller.groups.res.GroupsFindRes;
import github.oineh.monitoring.domain.groups.GroupsService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class ApiGroupsController {

    private final GroupsService groupsService;

    @PostMapping("/list")
    public ResponseEntity<List<GroupsFindRes>> list(Principal principal) {
        List<GroupsFindRes> listRes = groupsService.findList(principal.getName());

        return ResponseEntity.ok(listRes);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestParam("name") String name, Principal principal) {
        groupsService.add(principal.getName(), name);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> invite(GroupInviteReq req, Principal principal) {
        groupsService.targetUserInvite(req, principal.getName());

        return ResponseEntity.ok().build();
    }


}
