package github.oineh.monitoring.config.auth;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import github.oineh.monitoring.config.auth.jwt.JWTUtil;
import github.oineh.monitoring.config.auth.jwt.JwtAuthenticationFilter;
import github.oineh.monitoring.config.auth.jwt.JwtAuthorizationFilter;
import github.oineh.monitoring.domain.authority.Grade;
import github.oineh.monitoring.domain.user.UserService;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final AuthenticationConfiguration authConfig;
    private final JWTUtil jwtUtil;

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }


    @Bean
    public PasswordEncoder noOpPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        return http
            .csrf().disable()
            .formLogin(login -> login
                .loginPage("/login")
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .deleteCookies(JWTUtil.BEARER)
                .clearAuthentication(true)
            )
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService))
            .authorizeRequests(authroize -> authroize
                .antMatchers("/", "/singup", "/login").permitAll()
                .anyRequest().hasAnyAuthority(Grade.USER.getAuthority())
            ).build();

    }

}
