package github.oineh.monitoring.config.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class VerifyResult {

    private String userId;
    private boolean result;
}
