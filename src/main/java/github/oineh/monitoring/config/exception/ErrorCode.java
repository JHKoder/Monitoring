package github.oineh.monitoring.config.exception;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "서버 오류"),

    REFRESH_TOKEN_EXPIRE(401, "리프레쉬 토큰 만료"),

    NOT_FOUND_USER(400, "유저 정보를 찾을 수 없습니다."),
    NOT_FOUND_NICKNAME(400, "존재하지 않는 닉네임 입니다."),
    INVALID_VALUE(400, "유효하지 않은 값 입니다."),
    NONE_TOKEN_TYPE(401, "알수없는 토큰 타입"),
    REFRESH_TOKEN_NEED(401, "리프레쉬 토큰이 필요합니다."),
    NOT_USER_AUTH(401, "유저의 권한이 존재하지 않습니다.");

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
