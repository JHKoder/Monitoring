package github.oineh.monitoring.controller.group;

import github.oineh.monitoring.controller.group.req.GroupCreateReq;
import github.oineh.monitoring.controller.group.res.GroupListRes;
import github.oineh.monitoring.domain.group.GroupService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class ApiGroupController {

    private final GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupListRes> groupList(@PathVariable Long groupId, Principal principal) {
        GroupListRes res = groupService.findGroup(groupId, principal.getName());

        return ResponseEntity.ok(res);
    }

    @PostMapping("/add")
    public void createGroup(GroupCreateReq req, Principal principal) {

    }

    @PostMapping("/invite")
    public void invite() {

    }

    @GetMapping("/invite")
    public void inviteList() {

    }

    @PostMapping("/get")
    public void inviteTake() {

    }

}
