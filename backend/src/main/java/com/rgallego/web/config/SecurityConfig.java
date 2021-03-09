package com.rgallego.web.config;

import com.rgallego.web.repositories.UserRepository;
import com.rgallego.web.security.JwtTokenAuthenticationFilter;
import com.rgallego.web.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http,
                                              JwtTokenProvider tokenProvider,
                                              ReactiveAuthenticationManager reactiveAuthenticationManager) {
    final String BASE_PATH = "/api/**";
    final String LOGIN_PATH = "/login";
    final String USER_PATH = "/api/user/**";

    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .authenticationManager(reactiveAuthenticationManager)
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authorizeExchange(it -> it
//                        .pathMatchers(HttpMethod.GET, PATH_POSTS).permitAll()
//                            .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Fix para el error de CORS
//                            .pathMatchers(USER_PATH).hasAuthority("ADMIN")
//                            .pathMatchers(BASE_PATH).authenticated()
//                            .pathMatchers(LOGIN_PATH).permitAll()
//                        .pathMatchers("/me").authenticated()
//                        .pathMatchers("/users/{user}/**").access(this::currentUserMatchesPath)
                            .anyExchange().permitAll()
            )
            .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
            .build();


  }

  @Bean
  public ReactiveUserDetailsService userDetailsService(UserRepository users) {
    return username -> users.findByUsername(username)
            .map(u -> User
                    .withUsername(u.getUsername()).password(u.getPassword())
                    .authorities(u.getRole())
                    .build()
            );
  }

  @Bean
  public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                     PasswordEncoder passwordEncoder) {
    var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    authenticationManager.setPasswordEncoder(passwordEncoder);
    return authenticationManager;
  }

}
