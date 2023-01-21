package github.oineh.monitoring.common;


import github.oineh.monitoring.auth.domain.AuthRepository;
import github.oineh.monitoring.connect.domain.ConnectRepository;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.group.dept.domain.DeptRepository;
import github.oineh.monitoring.groups.group.dept.team.domain.TeamRepository;
import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedGroupRepository;
import github.oineh.monitoring.groups.invit.domain.InvitedGroupsRepository;
import github.oineh.monitoring.pc.domain.PcRepository;
import github.oineh.monitoring.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

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
