package com.project.MyBank.controllers.advices;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.MyBank.domain.exceptions.CookieNotFoundException;
import com.project.MyBank.domain.exceptions.IncorrectPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundHandler(UsernameNotFoundException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<String> jwtCreationHandler(JWTCreationException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao criar o token JWT");
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<String> jwtValidationHandler(JWTVerificationException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao verificar o token JWT");
    }

    @ExceptionHandler(CookieNotFoundException.class)
    public ResponseEntity<String> cookieNotFoundHandler(CookieNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> incorrectPasswordHandler(IncorrectPasswordException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
