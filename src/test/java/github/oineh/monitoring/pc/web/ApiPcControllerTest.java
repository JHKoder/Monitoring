package github.oineh.monitoring.pc.web;

import github.oineh.monitoring.common.IntegrationTest;
import github.oineh.monitoring.config.exception.ApiException;
import github.oineh.monitoring.config.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("api PC web 테스트")
public class ApiPcControllerTest extends IntegrationTest {


    static final String TARGET_RESOURCE = "/api/pc";

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("팀 초대 수락 테스트")
    void acceptTeamInvite() throws Exception {
        throw new ApiException(ErrorCode.NOT_FOUND_USER);
    }
}
