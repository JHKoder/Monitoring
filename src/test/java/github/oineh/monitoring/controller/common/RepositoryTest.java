package github.oineh.monitoring.controller.common;


import github.oineh.monitoring.domain.authority.AuthRepository;
import github.oineh.monitoring.domain.connect.ConnectRepository;
import github.oineh.monitoring.domain.groups.GroupsRepository;
import github.oineh.monitoring.domain.groups.group.category.DeptRepository;
import github.oineh.monitoring.domain.groups.group.category.TeamRepository;
import github.oineh.monitoring.domain.groups.group.invit.InvitedGroupRepository;
import github.oineh.monitoring.domain.groups.invit.InvitedGroupsRepository;
import github.oineh.monitoring.domain.user.UserRepository;
import github.oineh.monitoring.domain.user.pc.PcRepository;
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
