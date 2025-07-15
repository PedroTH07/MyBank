package com.project.MyBank.controllers.advices;

import com.project.MyBank.domain.exceptions.InsufficientMoneyException;
import com.project.MyBank.domain.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.net.MalformedURLException;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<String> malFormedUrlHandler(MalformedURLException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(InsufficientMoneyException.class)
    public ResponseEntity<String> insufficientMoneyHandler(InsufficientMoneyException e) {
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .body(e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> ioHandler(IOException e) {
        System.out.println(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("erro ao ler o arquivo");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentHandler(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userExistsHandler(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
