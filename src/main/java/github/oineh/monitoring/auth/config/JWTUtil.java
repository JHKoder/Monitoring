package github.oineh.monitoring.auth.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import github.oineh.monitoring.auth.token.TokenType;
import github.oineh.monitoring.auth.token.VerifyResult;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
public class JWTUtil {

    public static final String BEARER = "Bearers";
    public static final String BEARER_REFRESH = "Monitoring";
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("x509");

    public String generate(String userId, TokenType tokenType) {
        return makeAuthToken(userId, tokenType);
    }

    public static String makeAuthToken(String userId, TokenType tokenType) {
        return JWT.create()
                .withSubject(userId)
                .withClaim("exp", Instant.now().getEpochSecond() + tokenType.life())
                .sign(ALGORITHM);
    }

    public static VerifyResult verify(String token) {
        try {
            DecodedJWT jwt = JWT.require(ALGORITHM).build().verify(token);
            return VerifyResult.builder().result(true).userId(jwt.getSubject())
                    .build();
        } catch (Exception e) {
            DecodedJWT jwt = JWT.decode(token);
            return VerifyResult.builder().result(false).userId(jwt.getSubject())
                    .build();
        }
    }

}
