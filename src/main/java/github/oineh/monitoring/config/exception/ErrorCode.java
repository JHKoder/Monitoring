package github.oineh.monitoring.config.exception;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버 오류"),
    INVALID_VALUE(400, "유효하지 않은 값 입니다."),

    NOT_FOUND_USER(400, "유저 정보를 찾을 수 없습니다."),
    NOT_FOUND_NICKNAME(400, "존재하지 않는 닉네임 입니다."),
    USERS_ALREADY_PRESENT(400, "이미 있는 사용자 입니다."),

    INTERNAL_CLIENT_IP_NOT_FOUND(400, "IP 조회 불가"),
    INTERNAL_CLIENT_IP6V_NO(400, "ip6v 는 지원하지 않습니다."),
    INTERNAL_CLIENT_IP_NONE(400, "클라이언트 IP 정보가 확인 되지 않습니다."),

    NOT_FOUND_GROUPS(400, "그룹 정보가 없습니다."),
    NOT_FOUND_TEAM(400, "팀 정보가 없습니다."),

    NOT_FOUND_TARGET_USER(400, "상대 유저 정보를 찾지 못했습니다."),
    NOT_FOUND_SEND_USER(400, "알수없는 회원이 초대를 보냈습니다."),
    NO_GROUP_INVITES(400, "해당 그룹의 초대 기록이 없습니다."),

    SELECT_GROUP_USER(400, "해당 그룹의 맴버에 회원님이 없습니다."),
    NOT_TEAM_IN_USER(400, "해당 팀에 회원님이 없습니다."),
    NOT_GROUPS_DEPT(400, "해당 그룹네 부서정보가 없습니다."),

    NO_TEAM_INVITES(400, "해당 팀에 초대 기록이 없습니다."),
    YOUR_NOT_TEAM(400, "회원님의 팀이 아닙니다."),
    YOUR_NOT_GROUP(400, "회원님의 그룹이 아닙니다."),
    OVERLAP_INVITED_TEAM(400, "팀에서 이미 초대한 회원 입니다."),
    OVERLAP_INVITED_GROUPS(400, "그룹에서 이미 초대한 회원 입니다."),
    EMAIL_ALREADY_PRESENT(400, "이미 사용중인 이메일 입니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.message = message;
        this.status = status;
    }

    public static ErrorCode fromMessage(String message) {
        return Arrays.stream(ErrorCode.values())
            .filter(errorCode -> errorCode.getMessage().equals(message))
            .findFirst()
            .orElse(null);
    }
}
