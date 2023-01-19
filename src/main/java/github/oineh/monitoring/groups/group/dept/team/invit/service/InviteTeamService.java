package github.oineh.monitoring.groups.group.dept.team.invit.service;

import static github.oineh.monitoring.config.exception.ErrorCode.NOT_FOUND_TARGET_USER;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.groups.group.dept.team.domain.Team;
import github.oineh.monitoring.groups.group.dept.team.domain.TeamRepository;
import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedGroupRepository;
import github.oineh.monitoring.groups.group.dept.team.invit.domain.InvitedTeam;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteAcceptReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteCancelReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.req.TeamInviteReq;
import github.oineh.monitoring.groups.group.dept.team.invit.web.res.InviteTeamUserRes;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InviteTeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final InvitedGroupRepository invitedGroupRepository;


    @Transactional(readOnly = true)
    public List<InviteTeamUserRes> findInvite(String userId) {
        return mapInvitationsToResponse(getInvitationsByTargetUser(findUser(userId)));
    }

    @Transactional
    public void inviteUser(TeamInviteReq req, String userId) {
        Team team = findTeam(req.getTeamId());
        User targetUser = findUserEmail(req.getEmail());
        User sendUser = findSendUser(userId);

        validateGroupInMember(sendUser, team);
        validateTeamsAlreadyInvited(targetUser, team);

        invitedGroupRepository.save(new InvitedTeam(targetUser, sendUser, team));
    }

    @Transactional
    public void acceptInvite(TeamInviteAcceptReq req, String userId) {
        User user = findSendUser(userId);
        Team team = findTeam(req.getTeamId());

        checkInvitedTargetUserAndTeamOK(user, team);

        team.updateMember(user);
        invitedGroupRepository.delete(findTeamInvite(user, team));
    }

    @Transactional
    public void cancelInvite(TeamInviteCancelReq req, String userId) {
        User user = findUser(userId);
        Team team = findTeam(req.getTeamId());
        InvitedTeam invited = findTeamInvite(user, team);

        checkInvitedTargetUserAndTeamOK(user, team);
        invitedGroupRepository.delete(invited);
    }

    private InvitedTeam findTeamInvite(User user, Team team) {
        return invitedGroupRepository.findByTargetUserAndTeam(user, team)
                .orElseThrow(() -> new ApiException(ErrorCode.NO_TEAM_INVITES));
    }

    private void checkInvitedTargetUserAndTeamOK(User targetUser, Team team) {
        if (invitedGroupRepository.findByTargetUserAndTeam(targetUser, team).isEmpty()) {
            throw new ApiException(ErrorCode.NO_TEAM_INVITES);
        }
    }

    private User findSendUser(String userId) {
        return userRepository.findByLoginId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_SEND_USER));
    }

    private Team findTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));
    }

    private User findUserEmail(String email) {
        return userRepository.findByInformationEmail(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_TARGET_USER));
    }

    private void validateGroupInMember(User sendUser, Team team) {
        if (!team.checkMember(sendUser)) {
            throw new ApiException(ErrorCode.YOUR_NOT_TEAM);
        }
    }

    private void validateTeamsAlreadyInvited(User targetUser, Team team) {
        if (invitedGroupRepository.findByTargetUserAndTeam(targetUser, team).isPresent()) {
            throw new ApiException(ErrorCode.OVERLAP_INVITED_TEAM);
        }
    }

    private List<InviteTeamUserRes> mapInvitationsToResponse(List<InvitedTeam> invited) {
        return invited.stream().map(InviteTeamUserRes::new).collect(Collectors.toList());
    }

    private List<InvitedTeam> getInvitationsByTargetUser(User user) {
        return invitedGroupRepository.findByTargetUser(user)
                .orElse(Collections.emptyList());
    }

    private User findUser(String userId) {
        return userRepository.findByLoginId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }
}
