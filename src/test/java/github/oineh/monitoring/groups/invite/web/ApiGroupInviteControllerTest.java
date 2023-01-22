package github.oineh.monitoring.groups.invite.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.groups.domain.Groups;
import github.oineh.monitoring.groups.domain.GroupsRepository;
import github.oineh.monitoring.groups.invit.domain.InvitedGroups;
import github.oineh.monitoring.groups.invit.domain.InvitedGroupsRepository;
import github.oineh.monitoring.groups.invit.web.req.GroupInviteReq;
import github.oineh.monitoring.groups.invit.web.req.GroupInviteSendReq;
import github.oineh.monitoring.user.domain.User;
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

@DisplayName("api 그룹 초대장 web 테스 ")
public class ApiGroupInviteControllerTest extends IntegrationTest {

    static final String TARGET_RESOURCE = "/api/group/invite";

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
        User.Information information = new User.Information("test_email_@test.com", "test_name", "test_Nickname");
        adminUser = userRepository.save(new User("test_admin_id", "password", information));

        User.Information userInfo = new User.Information("test@test.com", "test_name", "test_Nickname");
        user = userRepository.save(new User("test_user_id", "password", userInfo));

        User.Information targetInfo = new User.Information("test_target_email@test.com", "test_name", "test_Nickname");
        targetUser = userRepository.save(new User("", "", targetInfo));

        groups = groupsRepository.save(new Groups(adminUser, "group_name"));
    }

    @Test
    @DisplayName("그룹 초대장 리스트 가져오기 테스트")
    void findGroupsInvite() throws Exception {
        //given
        InvitedGroups invited = new InvitedGroups(user, adminUser, groups);
        invitedGroupsRepository.save(invited);

        //when
        ResultActions action = mvc.perform(get(TARGET_RESOURCE));

        //then
        action.andExpect(status().isOk()).andExpect(jsonPath("$[0].groupsName").value(groups.getName()))
                .andExpect(jsonPath("$[0].sendName").value(adminUser.getInformation().getName()));
    }


    @Test
    @DisplayName("그룹 초대 하기 테스트")
    void invite() throws Exception {
        //given
        groups.updateMember(user);
        GroupInviteSendReq req = new GroupInviteSendReq(groups.getId(), targetUser.getEmail());

        //when
        ResultActions action = mvc.perform(post(TARGET_RESOURCE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("그룹 초대 수락 테스트")
    void acceptGroupsInvite() throws Exception {
        //given
        InvitedGroups invited = invitedGroupsRepository.save(new InvitedGroups(user, adminUser, groups));
        GroupInviteReq req = new GroupInviteReq(invited.getId(), groups.getId());

        //when
        ResultActions action = mvc.perform(
                patch(TARGET_RESOURCE).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }

    @Test
    @DisplayName("그룹 초대 거부 테스트")
    void cancelGroupsInvite() throws Exception {
        //given
        InvitedGroups invited = invitedGroupsRepository.save(new InvitedGroups(user, adminUser, groups));
        GroupInviteReq req = new GroupInviteReq(invited.getId(), groups.getId());

        //when
        ResultActions action = mvc.perform(
                delete(TARGET_RESOURCE).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(req)));

        //then
        action.andExpect(status().isOk());
    }
}