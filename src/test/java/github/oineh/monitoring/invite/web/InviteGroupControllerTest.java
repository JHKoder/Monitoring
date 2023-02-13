package github.oineh.monitoring.invite.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.invit.domain.InvitedGroups;
import github.oineh.monitoring.invit.domain.InvitedGroupsRepository;
import github.oineh.monitoring.invit.web.req.InviteGroupRequest;
import github.oineh.monitoring.invit.web.req.InviteGroupSendRequest;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.User.Information;
import github.oineh.monitoring.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("api 그룹 초대장")
public class InviteGroupControllerTest extends IntegrationTest {

    static final String url = "/api/group/invite";
    User adminUser;
    Groups groups;
    User user;
    User targetUser;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupsRepository groupsRepository;
    @Autowired
    private InvitedGroupsRepository invitedGroupsRepository;

    @BeforeEach
    void setup() {
        adminUser = createUser("test_admin_id", "test_email_@test.com");
        user = createUser("test_user_id", "test@test.com");
        targetUser = createUser("", "test_target_email@test.com");

        groups = groupsRepository.save(new Groups(adminUser, "group_name"));
    }

    @Test
    @DisplayName("리스트 가져오기")
    void findGroupsInvite() throws Exception {
        //given
        invitedGroupsRepository.save(new InvitedGroups(user, adminUser, groups));

        //when
        ResultActions action = mvc.perform(get(url));

        //then
        action.andExpect(status().isOk()).andExpect(jsonPath("$[0].groupsName").value(groups.getName()))
                .andExpect(jsonPath("$[0].sendName").value(adminUser.getInformation().getName()));
    }

    @Test
    @DisplayName("보내기")
    void invite() throws Exception {
        //given
        groups.updateMember(user);
        InviteGroupSendRequest req = new InviteGroupSendRequest(groups.getId(), targetUser.getEmail());

        //when
        ResultActions action = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("수락")
    void acceptGroupsInvite() throws Exception {
        //given
        InvitedGroups invited = invitedGroupsRepository.save(new InvitedGroups(user, adminUser, groups));
        InviteGroupRequest req = new InviteGroupRequest(invited.getId(), groups.getId());

        //when
        ResultActions action = mvc.perform(
                patch(url).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("거부")
    void cancelGroupsInvite() throws Exception {
        //given
        InvitedGroups invited = invitedGroupsRepository.save(new InvitedGroups(user, adminUser, groups));
        InviteGroupRequest req = new InviteGroupRequest(invited.getId(), groups.getId());

        //when
        ResultActions action = mvc.perform(
                delete(url).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    private User createUser(String id, String email) {
        Information userInfo = new Information(email, "test_name", "test_Nickname");
        return userRepository.save(new User(id, "password", userInfo));
    }
}
