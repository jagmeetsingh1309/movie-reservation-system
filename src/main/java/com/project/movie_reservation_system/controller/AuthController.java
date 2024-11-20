package com.project.movie_reservation_system.controller;

import com.project.movie_reservation_system.dto.AuthRequestDto;
import com.project.movie_reservation_system.dto.AuthResponseDto;
import com.project.movie_reservation_system.dto.SignupRequestDto;
import com.project.movie_reservation_system.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signupUser(@RequestBody SignupRequestDto signupRequestDto){
        String token = authService.signupUser(signupRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AuthResponseDto.builder().token(token).build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> authenticateUser(@RequestBody AuthRequestDto authRequestDto){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword());
        authenticationManager.authenticate(authToken);
        String token = authService.authenticateUser(authRequestDto.getUsername());
        return ResponseEntity.ok(AuthResponseDto.builder().token(token).build());
    }

}
