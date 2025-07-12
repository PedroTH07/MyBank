package com.project.MyBank.services;

import com.project.MyBank.domain.dtos.PaymentRequestDto;
import com.project.MyBank.domain.User;
import com.project.MyBank.domain.UserRepository;
import com.project.MyBank.domain.dtos.UserResponseDto;
import com.project.MyBank.domain.exceptions.CookieNotFoundException;
import com.project.MyBank.infra.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;

    public List<User> findAll() { return this.repository.findAll(); }

    public UserResponseDto thisUser(HttpServletRequest request) throws CookieNotFoundException, UsernameNotFoundException {
        var token = this.jwtService.recoverToken(request);
        if (token == null) throw new CookieNotFoundException("cookie jwt não encontrado em /users/me");

        String subject = this.jwtService.validateToken(token);

        User user = this.repository.findByEmail(subject).orElseThrow(() -> new UsernameNotFoundException("user not found at /users/me"));
        return new UserResponseDto(user);
    }

    public ResponseEntity<?> pay(PaymentRequestDto data) {
        return ResponseEntity.badRequest().body("depois eu termino");
    }

}
