package github.oineh.monitoring.auth.filter;

import github.oineh.monitoring.auth.config.JWTUtil;
import github.oineh.monitoring.auth.token.UserLogin;
import github.oineh.monitoring.auth.token.VerifyResult;
import github.oineh.monitoring.user.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static github.oineh.monitoring.auth.config.JWTUtil.BEARER;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final LoginService loginService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, LoginService loginService) {
        super(authenticationManager);
        this.loginService = loginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("");
        Cookie[] cookies = request.getCookies();
        Cookie cookie = parseCookie(cookies);

        if (validCookie(cookie)) {
            chain.doFilter(request, response);
            return;
        }

        log.info("");
        VerifyResult result = JWTUtil.verify(parseToken(cookie));

        if (result.tokenExistence()) {
            UserLogin user = (UserLogin) loginService.loadUserByUsername(result.getUserId());
            setSecurityContextAuth(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        }
        if (result.tokenNone()) {
            redirectToLogout(response);
        }

        chain.doFilter(request, response);
    }

    private void redirectToLogout(HttpServletResponse response) throws IOException {
        response.sendRedirect("/logout");
    }

    private void setSecurityContextAuth(UsernamePasswordAuthenticationToken token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private String parseToken(Cookie cookie) {
        return cookie.getValue().substring(BEARER.length());
    }

    private Cookie parseCookie(Cookie[] cookies) {

        if (cookies == null) {
            return null;
        }

        return Arrays.stream(cookies)
                .filter(bear -> bear.getName().equals(BEARER))
                .findFirst()
                .orElse(null);
    }

    private boolean validCookie(Cookie cookie) {
        return cookie == null;
    }
}
