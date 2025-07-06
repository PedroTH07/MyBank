package com.project.MyBank.services;

import com.project.MyBank.domain.User;
import com.project.MyBank.domain.UserRepository;
import com.project.MyBank.domain.UserRequestDto;
import com.project.MyBank.domain.UserResponseDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    public AuthService(PasswordEncoder encoder, UserRepository repository) {
        this.encoder = encoder;
        this.repository = repository;
    }

    public UserResponseDto register(UserRequestDto data) {
        String userPassword = this.encoder.encode(data.password());
        User user = new User();
        user.setName(data.name());
        user.setEmail(data.email());
        user.setPassword(userPassword);
        user.setMoney(999.99);

        return new UserResponseDto(this.repository.save(user));
    }
}
