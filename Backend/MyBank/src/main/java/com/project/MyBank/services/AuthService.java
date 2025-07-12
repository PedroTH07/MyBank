package com.project.MyBank.services;

import com.project.MyBank.domain.User;
import com.project.MyBank.domain.UserRepository;
import com.project.MyBank.domain.dtos.UserRequestDto;
import com.project.MyBank.domain.dtos.UserResponseDto;
import com.project.MyBank.domain.exceptions.CookieNotFoundException;
import com.project.MyBank.domain.exceptions.IncorrectPasswordException;
import com.project.MyBank.domain.exceptions.UserAlreadyExistsException;
import com.project.MyBank.infra.security.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
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

    @Transactional
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

        String token = this.jwtService.generateToken(user.getEmail());
        Cookie cookie = this.createAccessCookie(token);

        String refreshToken = this.jwtService.generateRefreshToken(user);
        Cookie refreshCookie = this.createRefreshCookie(refreshToken);

        response.addCookie(cookie);
        response.addCookie(refreshCookie);

        return new UserResponseDto(this.repository.save(user));
    }

    public UserResponseDto login(UserRequestDto data, HttpServletResponse response) throws UsernameNotFoundException {
        User user = this.repository.findByEmail(data.email()).orElseThrow(() -> new UsernameNotFoundException("user not found at login"));

        if (!this.encoder.matches(data.password(), user.getPassword())) {
            throw new IncorrectPasswordException("senha incorreta");
        }

        String token = this.jwtService.generateToken(user.getEmail());
        Cookie cookie = createAccessCookie(token);

        String refreshToken = this.jwtService.generateRefreshToken(user);
        Cookie refreshCookie = createRefreshCookie(refreshToken);

        response.addCookie(cookie);
        response.addCookie(refreshCookie);

        return new UserResponseDto(user);
    }

    public void refresh(String token, HttpServletResponse response) throws CookieNotFoundException {
        if (token == null) throw new CookieNotFoundException("refresh token not found");

        var validateToken = this.jwtService.validateToken(token);
        var accessToken = this.jwtService.generateToken(validateToken);

        Cookie cookie = createAccessCookie(accessToken);

        response.addCookie(cookie);
    }

    private Cookie createAccessCookie(String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(5 * 60);
        return cookie;
    }

    private Cookie createRefreshCookie(String token) {
        Cookie cookie = new Cookie("refresh_token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(60 * 60);
        return cookie;
    }
}
