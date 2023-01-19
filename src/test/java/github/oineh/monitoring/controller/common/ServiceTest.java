package github.oineh.monitoring.controller.common;

import github.oineh.monitoring.connect.service.ConnectService;
import github.oineh.monitoring.groups.group.service.GroupService;
import github.oineh.monitoring.groups.service.GroupsService;
import github.oineh.monitoring.pc.service.PcService;
import github.oineh.monitoring.user.service.SignUpService;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;

@Disabled
public class ServiceTest extends RepositoryTest {

    @Autowired
    public SignUpService signUpService;

    @Autowired
    public GroupsService groupsService;
    @Autowired
    public GroupService groupService;
    @Autowired
    public ConnectService connectService;
    @Autowired
    public PcService pcService;

}
