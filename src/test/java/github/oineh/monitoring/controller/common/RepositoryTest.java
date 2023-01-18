package github.oineh.monitoring.controller.common;


import github.oineh.monitoring.authority.domain.AuthRepository;
import github.oineh.monitoring.connect.domain.ConnectRepository;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.domain.DeptRepository;
import github.oineh.monitoring.groups.group.team.domain.TeamRepository;
import github.oineh.monitoring.groups.group.invit.InvitedGroupRepository;
import github.oineh.monitoring.groups.invit.InvitedGroupsRepository;
import github.oineh.monitoring.user.domain.UserRepository;
import github.oineh.monitoring.user.pc.domain.PcRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
public class RepositoryTest {

    @Autowired
    public InvitedGroupsRepository invitedGroupsRepository;
    @Autowired
    public InvitedGroupRepository invitedGroupRepository;

    @Autowired
    public AuthRepository authRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public PcRepository pcRepository;
    @Autowired
    public ConnectRepository connectRepository;

    @Autowired
    public GroupsRepository groupsRepository;
    @Autowired
    public TeamRepository teamRepository;
    @Autowired
    public DeptRepository deptRepository;
}
