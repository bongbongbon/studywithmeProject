package com.personal.project.common.config;

import com.personal.project.jwt.CustomLogoutFilter;
import com.personal.project.jwt.JWTFilter;
import com.personal.project.jwt.JWTUtil;
import com.personal.project.jwt.LoginFilter;
import com.personal.project.repository.RefreshRepository;
import com.personal.project.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{

        return configuration.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
            http
                    .csrf((auth) -> auth.disable());
            http
                    .formLogin((auth) -> auth.disable());
            http
                    .httpBasic((auth) -> auth.disable());
            http
                    .authorizeHttpRequests((auth) -> auth
                            .requestMatchers("/login", "/", "/join", "/**").permitAll()
                            .requestMatchers("/admin").hasRole("ADMIN")
                            .requestMatchers("/reissue").permitAll()
                            .anyRequest().authenticated());
            http
                    .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
            http
                    .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository, refreshTokenRedisRepository), UsernamePasswordAuthenticationFilter.class);
            http
                    .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository, refreshTokenRedisRepository), LogoutFilter.class);
            http
                    .sessionManagement((session) -> session
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            return http.build();
    }
}
