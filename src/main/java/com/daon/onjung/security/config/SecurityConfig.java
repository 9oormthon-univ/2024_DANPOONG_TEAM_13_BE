package com.daon.onjung.security.config;

import com.daon.onjung.core.constant.Constants;
import com.daon.onjung.core.utility.JsonWebTokenUtil;
import com.daon.onjung.security.application.usecase.AuthenticateJsonWebTokenUseCase;
import com.daon.onjung.security.application.usecase.AuthenticateOauthUseCase;
import com.daon.onjung.security.filter.ExceptionFilter;
import com.daon.onjung.security.filter.GlobalLoggerFilter;
import com.daon.onjung.security.filter.JsonWebTokenAuthenticationFilter;
import com.daon.onjung.security.filter.OauthAuthenticationFilter;
import com.daon.onjung.security.handler.common.DefaultAccessDeniedHandler;
import com.daon.onjung.security.handler.common.DefaultAuthenticationEntryPoint;
import com.daon.onjung.security.handler.login.DefaultLoginFailureHandler;
import com.daon.onjung.security.handler.login.DefaultLoginSuccessHandler;
import com.daon.onjung.security.handler.login.OauthLoginSuccessHandler;
import com.daon.onjung.security.handler.logout.DefaultLogoutProcessHandler;
import com.daon.onjung.security.handler.logout.DefaultLogoutSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DefaultLoginSuccessHandler defaultLoginSuccessHandler;
    private final DefaultLoginFailureHandler defaultLoginFailureHandler;

    private final DefaultLogoutProcessHandler defaultLogoutProcessHandler;
    private final DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

    private final OauthLoginSuccessHandler oauthLoginSuccessHandler;

    private final DefaultAccessDeniedHandler defaultAccessDeniedHandler;
    private final DefaultAuthenticationEntryPoint defaultAuthenticationEntryPoint;

    private final AuthenticateJsonWebTokenUseCase authenticateJsonWebTokenUseCase;
    private final AuthenticateOauthUseCase authenticateOauthUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(configurer -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers(Constants.NO_NEED_AUTH_URLS.toArray(String[]::new)).permitAll()
                        .requestMatchers(Constants.USER_URLS.toArray(String[]::new)).hasAnyRole("USER", "OWNER")
                        .anyRequest().authenticated()
                )

                .formLogin(configurer -> configurer
                        .loginPage("/login")
                        .loginProcessingUrl("/api/v1/auth/login")
                        .usernameParameter("serial_id")
                        .passwordParameter("password")
                        .successHandler(defaultLoginSuccessHandler)
                        .failureHandler(defaultLoginFailureHandler)
                )

                .logout(configurer -> configurer
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(defaultLogoutProcessHandler)
                        .logoutSuccessHandler(defaultLogoutSuccessHandler)
                )

                .exceptionHandling(configurer -> configurer
                        .accessDeniedHandler(defaultAccessDeniedHandler)
                        .authenticationEntryPoint(defaultAuthenticationEntryPoint)
                )

                .addFilterBefore(
                        new OauthAuthenticationFilter(
                                "/api/v1/oauth/login",
                                oauthLoginSuccessHandler,
                                authenticateOauthUseCase
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )

                .addFilterBefore(
                        new JsonWebTokenAuthenticationFilter(
                                authenticateJsonWebTokenUseCase,
                                jsonWebTokenUtil
                        ),
                        LogoutFilter.class
                )

                .addFilterBefore(
                        new ExceptionFilter(),
                        JsonWebTokenAuthenticationFilter.class
                )

                .addFilterBefore(
                        new GlobalLoggerFilter(),
                        ExceptionFilter.class
                )

                .getOrBuild();
    }
}

