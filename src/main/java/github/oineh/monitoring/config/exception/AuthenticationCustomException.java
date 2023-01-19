package github.oineh.monitoring.config.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AuthenticationCustomException extends AuthenticationException {

    private final ErrorCode errorCode;


    public AuthenticationCustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
