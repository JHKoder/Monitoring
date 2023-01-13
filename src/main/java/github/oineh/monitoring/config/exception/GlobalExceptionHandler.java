package github.oineh.monitoring.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handlerServerException(Exception e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ErrorCode> handlerApiException(ApiException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(e.getErrorCode());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handlerIllegalArgumentExceptionException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_VALUE);
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

}
