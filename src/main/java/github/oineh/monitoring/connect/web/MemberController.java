package github.oineh.monitoring.connect.web;

import github.oineh.monitoring.connect.service.ConnectService;
import github.oineh.monitoring.connect.web.res.MemberPingResponse;
import github.oineh.monitoring.connect.web.res.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/member/teams")
@RequiredArgsConstructor
public class MemberController {

    private final ConnectService connectService;

    @GetMapping("/{teamId}")
    public List<MemberResponse> findMembers(@PathVariable("teamId") Long teamId) {
        return connectService.findMember(teamId);
    }

    @GetMapping("/{teamId}/connects/{connectId}")
    public MemberPingResponse connectionMember(@PathVariable("teamId") Long teamId, @PathVariable Long connectId) {
        return connectService.findMemberPing(teamId, connectId);
    }
}
