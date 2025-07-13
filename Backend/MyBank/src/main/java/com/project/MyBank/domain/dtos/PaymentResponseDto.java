package com.project.MyBank.domain.dtos;

import java.util.List;

public record PaymentResponseDto(
        Double amount,
        List<UserPaymentResponseDto> users
) {
}
