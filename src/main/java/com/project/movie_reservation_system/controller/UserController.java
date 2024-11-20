package com.project.movie_reservation_system.controller;

import com.project.movie_reservation_system.dto.ApiResponseDto;
import com.project.movie_reservation_system.dto.UserResponseDto;
import com.project.movie_reservation_system.enums.Role;
import com.project.movie_reservation_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.project.movie_reservation_system.constant.ExceptionMessages.USER_NOT_FOUND;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/me")
    public ResponseEntity<UserResponseDto> currentUser() {
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(currentUsername)
                .map(user -> ResponseEntity.ok(UserResponseDto.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .role(user.getRole())
                        .username(user.getUsername())
                        .id(user.getId())
                        .build())
                )
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto> getAllUsers() {
        List<UserResponseDto> userResponseDtos = userRepository.findAll()
                .stream()
                .map(user -> UserResponseDto.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .role(user.getRole())
                        .username(user.getUsername())
                        .id(user.getId())
                        .build()
                )
                .toList();
        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .data(userResponseDtos)
                        .message("Fetched all users")
                        .build()
        );
    }

    @Secured({"ROLE_SUPER_ADMIN"})
    @PostMapping("/user/promote/{username}")
    public ResponseEntity<UserResponseDto> promoteUserToAdmin(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(userInDb -> {
                    userInDb.setRole(Role.ROLE_ADMIN);
                    return userRepository.save(userInDb);
                })
                .map(updatedUser -> ResponseEntity.ok(UserResponseDto.builder()
                        .email(updatedUser.getEmail())
                        .firstName(updatedUser.getFirstName())
                        .lastName(updatedUser.getLastName())
                        .role(updatedUser.getRole())
                        .username(updatedUser.getUsername())
                        .id(updatedUser.getId())
                        .build())
                )
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

}
