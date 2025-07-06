package com.project.MyBank.controllers.rest;

import com.project.MyBank.domain.UserRequestDto;
import com.project.MyBank.domain.UserResponseDto;
import com.project.MyBank.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto data, HttpServletResponse response) {
        return this.service.register(data, response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto data, HttpServletResponse response) {
        return this.service.login(data, response);
    }
}
