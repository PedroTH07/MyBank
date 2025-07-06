package com.project.MyBank.services;

import com.project.MyBank.domain.User;
import com.project.MyBank.domain.UserRepository;
import com.project.MyBank.domain.UserRequestDto;
import com.project.MyBank.domain.UserResponseDto;
import com.project.MyBank.infra.exceptions.UserAlreadyExistsException;
import com.project.MyBank.infra.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PasswordEncoder encoder;
    private final UserRepository repository;
    private final JwtService jwtService;

    public AuthService(PasswordEncoder encoder, UserRepository repository, JwtService jwt) {
        this.encoder = encoder;
        this.repository = repository;
        this.jwtService = jwt;
    }

    public UserResponseDto register(UserRequestDto data, HttpServletResponse response) throws UserAlreadyExistsException {
        var userExists = this.repository.findByEmail(data.email());
        if (userExists.isPresent()) {
            throw new UserAlreadyExistsException("user already exists at register");
        }

        String userPassword = this.encoder.encode(data.password());
        User user = new User();
        user.setName(data.name());
        user.setEmail(data.email());
        user.setPassword(userPassword);
        user.setMoney(999.99);

        String token = this.jwtService.generateToken(user);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(5 * 60);

        response.addCookie(cookie);

        return new UserResponseDto(this.repository.save(user));
    }

    public ResponseEntity<?> login(UserRequestDto data, HttpServletResponse response) throws UsernameNotFoundException {
        User user = this.repository.findByEmail(data.email()).orElseThrow(() -> new UsernameNotFoundException("user not found at login"));

        if (this.encoder.matches(data.password(), user.getPassword())) {

            String token = this.jwtService.generateToken(user);

            Cookie cookie = new Cookie("jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(5 * 60);

            response.addCookie(cookie);

            return ResponseEntity.ok().body(new UserResponseDto(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("incorrect password");
    }
}
