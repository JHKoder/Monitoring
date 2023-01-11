package github.oineh.monitoring.config.auth.jwt;


import static github.oineh.monitoring.config.auth.jwt.JWTUtil.BEARER;

import github.oineh.monitoring.config.auth.UserLogin;
import github.oineh.monitoring.config.auth.VerifyResult;
import github.oineh.monitoring.domain.user.UserService;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserService userService) {
        super(authenticationManager);
        this.userService = userService;
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
            UserLogin user = (UserLogin) userService.loadUserByUsername(result.getUserId());
            SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
            chain.doFilter(request, response);
        }
    }
}
