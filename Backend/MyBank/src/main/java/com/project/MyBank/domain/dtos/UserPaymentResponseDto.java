package com.project.MyBank.domain.dtos;

import com.project.MyBank.domain.User;

public record UserPaymentResponseDto(
        String nome,
        String email
) {
    public UserPaymentResponseDto(User user) {
        this(user.getName(), user.getEmail());
    }
}
