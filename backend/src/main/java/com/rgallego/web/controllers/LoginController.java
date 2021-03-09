package com.rgallego.web.controllers;

import com.rgallego.web.documents.UserDocument;
import com.rgallego.web.repositories.UserRepository;
import com.rgallego.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class LoginController {

    private final JwtTokenProvider tokenProvider;

    private final ReactiveAuthenticationManager authenticationManager;

    @PostMapping("/login")
    public Mono<ResponseEntity> login(@Valid @RequestBody Mono<AuthenticationRequest> authRequest) {
        log.info("Login request received");
        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()))
                )
                .map(auth -> {
                    String jwt = tokenProvider.createToken(auth);
                    String role = auth.getAuthorities().iterator().next().getAuthority();
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                    var body = Map.of("accessToken", jwt, "role", role);
                    return new ResponseEntity<>(body, httpHeaders, HttpStatus.OK);
                });

    }
}
