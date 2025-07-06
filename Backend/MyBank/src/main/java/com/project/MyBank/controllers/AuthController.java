package com.project.MyBank.controllers;

import com.project.MyBank.domain.UserRequestDto;
import com.project.MyBank.domain.UserResponseDto;
import com.project.MyBank.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserResponseDto register(@RequestBody UserRequestDto data) {
        return this.service.register(data);
    }
}
