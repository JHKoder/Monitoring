package github.oineh.monitoring.auth.filter;

import github.oineh.monitoring.auth.config.JWTUtil;
import github.oineh.monitoring.auth.token.UserLogin;
import github.oineh.monitoring.auth.token.VerifyResult;
import github.oineh.monitoring.user.service.LoginService;
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

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final LoginService loginService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, LoginService loginService) {
        super(authenticationManager);
        this.loginService = loginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Cookie[] requestCookies = request.getCookies();
        if (requestCookies == null) {
            chain.doFilter(request, response);
            return;
        }

        Cookie cookie = Arrays.stream(requestCookies)
                .filter(bear -> bear.getName().equals(BEARER))
                .findFirst()
                .orElse(null);

        if (cookie == null) {
            chain.doFilter(request, response);
            return;
        }
        String token = cookie.getValue().substring(BEARER.length());
        VerifyResult result = JWTUtil.verify(token);

        if (!result.isResult()) {
            chain.doFilter(request, response);
            response.sendRedirect("/logout");
        }
        if (result.isResult()) {
            UserLogin user = (UserLogin) loginService.loadUserByUsername(result.getUserId());
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
            chain.doFilter(request, response);
        }
    }
}
