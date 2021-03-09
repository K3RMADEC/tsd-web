package com.rgallego.web.controllers;

import com.rgallego.web.bean.UserBean;
import com.rgallego.web.documents.UserDocument;
import com.rgallego.web.repositories.UserRepository;
import com.rgallego.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public Mono<ResponseEntity> register(@RequestBody Mono<UserDocument> user) {
        log.info("Register user request received");
        return user.flatMap(u -> {
            //Encrypt password
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            userRepository.saveUser(u);
            return Mono.just(new ResponseEntity<>(HttpStatus.OK));
        });
    }

    @DeleteMapping("/remove")
    public ResponseEntity remove(@RequestParam String username) {
        log.info("Remove user request received");
        userRepository.removeUser(username);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public Flux<UserBean> getAll() {
        log.info("Get All users request received");
        return userRepository.findAll().map(user -> new UserBean(user.getUsername(), user.getRole()));
    }

}
