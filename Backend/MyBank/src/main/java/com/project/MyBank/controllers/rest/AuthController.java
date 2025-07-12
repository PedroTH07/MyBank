package com.project.MyBank.controllers.rest;

import com.project.MyBank.domain.dtos.UserRequestDto;
import com.project.MyBank.domain.dtos.UserResponseDto;
import com.project.MyBank.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRequestDto data, HttpServletResponse response) {
        return this.service.register(data, response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody @Valid UserRequestDto data, HttpServletResponse response) {
        return ResponseEntity.ok(this.service.login(data, response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue(value = "refresh_token", required = false) String token,
            HttpServletResponse response
    ) {
        this.service.refresh(token, response);
        return ResponseEntity.ok().build();
    }
}
