package github.oineh.monitoring.controller.common;

import github.oineh.monitoring.domain.connect.ConnectService;
import github.oineh.monitoring.domain.groups.GroupsService;
import github.oineh.monitoring.domain.groups.group.GroupService;
import github.oineh.monitoring.domain.user.UserService;
import github.oineh.monitoring.domain.user.pc.PcService;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
public class ServiceTest extends RepositoryTest {

    @Autowired
    public UserService userService;

    @Autowired
    public GroupsService groupsService;
    @Autowired
    public GroupService groupService;
    @Autowired
    public ConnectService connectService;
    @Autowired
    public PcService pcService;

}
