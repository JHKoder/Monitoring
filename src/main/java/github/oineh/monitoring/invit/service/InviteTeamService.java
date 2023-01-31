package github.oineh.monitoring.invit.service;

import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import github.oineh.monitoring.invit.domain.InvitedGroupRepository;
import github.oineh.monitoring.invit.web.req.TeamInviteAcceptReq;
import github.oineh.monitoring.invit.web.req.TeamInviteCancelReq;
import github.oineh.monitoring.invit.web.req.TeamInviteReq;
import github.oineh.monitoring.invit.web.res.InviteTeamUserRes;
import github.oineh.monitoring.team.domain.Team;
import github.oineh.monitoring.team.domain.TeamRepository;
import github.oineh.monitoring.invit.domain.InvitedTeam;
import github.oineh.monitoring.user.domain.User;
import github.oineh.monitoring.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static github.oineh.monitoring.config.exception.ErrorCode.NOT_FOUND_TARGET_USER;

@Service
@RequiredArgsConstructor
public class InviteTeamService {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final InvitedGroupRepository invitedGroupRepository;


    @Transactional(readOnly = true)
    public List<InviteTeamUserRes> findInvite(String userId) {
        return convertInvitationsToRes(findUserByInvite(findUser(userId)));
    }

    @Transactional
    public void makeInvite(TeamInviteReq req, String userId) {
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

        validateInvitedTargetUserAndTeamOK(user, team);

        team.updateMember(user);
        invitedGroupRepository.delete(findInvite(user, team));
    }

    @Transactional
    public void cancelInvite(TeamInviteCancelReq req, String userId) {
        User user = findUser(userId);
        Team team = findTeam(req.getTeamId());
        InvitedTeam invited = findInvite(user, team);

        validateInvitedTargetUserAndTeamOK(user, team);

        invitedGroupRepository.delete(invited);
    }

    private User findUser(String userId) {
        return userRepository.findByLoginId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_USER));
    }

    private User findSendUser(String userId) {
        return userRepository.findByLoginId(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_SEND_USER));
    }

    private User findUserEmail(String email) {
        return userRepository.findByInformationEmail(email)
                .orElseThrow(() -> new ApiException(NOT_FOUND_TARGET_USER));
    }

    private Team findTeam(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_TEAM));
    }

    private InvitedTeam findInvite(User user, Team team) {
        return invitedGroupRepository.findByTargetUserAndTeam(user, team)
                .orElseThrow(() -> new ApiException(ErrorCode.NO_TEAM_INVITES));
    }

    private List<InvitedTeam> findUserByInvite(User user) {
        return invitedGroupRepository.findByTargetUser(user)
                .orElse(Collections.emptyList());
    }

    private void validateInvitedTargetUserAndTeamOK(User targetUser, Team team) {
        if (invitedGroupRepository.findByTargetUserAndTeam(targetUser, team).isEmpty()) {
            throw new ApiException(ErrorCode.NO_TEAM_INVITES);
        }
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

    private List<InviteTeamUserRes> convertInvitationsToRes(List<InvitedTeam> invited) {
        return invited.stream().map(InviteTeamUserRes::new).collect(Collectors.toList());
    }
}
