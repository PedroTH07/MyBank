package com.project.MyBank.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.MyBank.domain.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(String userEmail) throws JWTCreationException {
        return JWT.create()
                .withIssuer("MyBank")
                .withSubject(userEmail)
                .withExpiresAt(this.generateExpirationDate(5))
                .withClaim("type", "access")
                .sign(Algorithm.HMAC256(this.secret));
    }

    public String generateRefreshToken(User user) throws JWTCreationException {
        return JWT.create()
                .withIssuer("MyBank")
                .withSubject(user.getEmail())
                .withExpiresAt(this.generateExpirationDate(60))
                .withClaim("type", "refresh")
                .sign(Algorithm.HMAC256(this.secret));
    }

    public String validateToken(String token) throws JWTVerificationException {
        try {
            return JWT.require(Algorithm.HMAC256(this.secret))
                    .withIssuer("MyBank")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public String recoverToken(HttpServletRequest request) {
        var cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private Instant generateExpirationDate(Integer minutes) {
        return LocalDateTime.now().plusMinutes(minutes).toInstant(ZoneOffset.of("-03:00"));
    }
}
