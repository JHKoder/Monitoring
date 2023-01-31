package github.oineh.monitoring.auth.filter;

import github.oineh.monitoring.auth.config.JWTUtil;
import github.oineh.monitoring.auth.token.TokenType;
import github.oineh.monitoring.auth.token.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        validateMethodPost(request);

        String username = parseUsername(request);
        String password = parsePassword(request);

        return authenticationManager.authenticate(parseToken(username, password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        UserLogin userToken = UserLogin.ofPricipal(authResult.getPrincipal());

        response.addCookie(new Cookie(JWTUtil.BEARER,
                JWTUtil.BEARER + jwtUtil.generate(userToken.getUsername(), TokenType.ACCESS)));
        response.sendRedirect("/");
    }

    private void validateMethodPost(HttpServletRequest request) {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
    }

    private UsernamePasswordAuthenticationToken parseToken(String username, String password) {
        return UsernamePasswordAuthenticationToken.unauthenticated(username, password);
    }

    private String parsePassword(HttpServletRequest request) {
        String passWord = obtainPassword(request);
        return validParse(passWord);
    }

    private String parseUsername(HttpServletRequest request) {
        String userName = obtainUsername(request);
        return validParse(userName);
    }

    private String validParse(String parse) {
        return parse != null ? parse : "";
    }
}
