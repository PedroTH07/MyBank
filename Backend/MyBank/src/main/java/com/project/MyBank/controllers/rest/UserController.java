package com.project.MyBank.controllers.rest;

import com.project.MyBank.domain.dtos.PaymentRequestDto;
import com.project.MyBank.domain.User;
import com.project.MyBank.domain.dtos.UserResponseDto;
import com.project.MyBank.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(this.service.thisUser(request));
    }

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody @Valid PaymentRequestDto data) {
        return this.service.pay(data);
    }
}
