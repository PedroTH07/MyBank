package com.project.MyBank.controllers.rest;

import com.project.MyBank.domain.User;
import com.project.MyBank.domain.UserResponseDto;
import com.project.MyBank.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getUsers() {
        return this.service.findAll();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> thisUser(HttpServletRequest request) {
        return this.service.thisUser(request);
    }
}
