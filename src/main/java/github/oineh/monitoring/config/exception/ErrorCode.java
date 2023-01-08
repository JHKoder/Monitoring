package github.oineh.monitoring.config.exception;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버 오류"),


    NOT_FOUND_USER(400, "유저 정보를 찾을 수 없습니다."),
    NOT_FOUND_NICKNAME(400, "존재하지 않는 닉네임 입니다."),
    INVALID_VALUE(400, "유효하지 않은 값 입니다."),
    USERS_ALREADY_PRESENT(400, "이미 있는 사용자 입니다."),
    CLIENT_IP_NOT_FOUND(400, "IP 조회 불가"),
    CLIENT_IP6V_NO(400, "ip6v 는 지원하지 않습니다."),
    INTERNAL_CLIENT_IP_NONE_CHECK_ERROR(400, "클라이언트 IP 정보가 확인 되지 않습니다."),
    NOT_FOUND_GROUPS(400, "그룹 정보가 없습니다."),
    NOT_FOUND_TARGET_USER(400, "상대 유저 정보를 찾지 못했습니다."),
    NOT_FOUND_SEND_USER(400, "발송 유저 정보가 없습니다."),
    NO_GROUP_INVITES(400, "해당 그룹의 초대 기록이 없습니다.");
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
