package com.project.MyBank.controllers.rest;

import com.project.MyBank.domain.dtos.PaymentRequestDto;
import com.project.MyBank.domain.User;
import com.project.MyBank.domain.dtos.PaymentResponseDto;
import com.project.MyBank.domain.dtos.UserResponseDto;
import com.project.MyBank.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PutMapping("/image")
    public ResponseEntity<String> editImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        return ResponseEntity.ok(this.service.editImage(file, request));
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentResponseDto> pay(@RequestBody @Valid PaymentRequestDto data, HttpServletRequest request) {
        return ResponseEntity.ok(this.service.pay(data, request));
    }
}
