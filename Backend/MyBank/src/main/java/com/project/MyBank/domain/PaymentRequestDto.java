package com.project.MyBank.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record PaymentRequestDto(
        @org.hibernate.validator.constraints.UUID(message = "precisa ser um UUID")
        @NotBlank(message = "precisa ter um pagador")
        UUID payerId,

        @org.hibernate.validator.constraints.UUID(message = "precisa ser um UUID")
        @NotBlank(message = "precisa ter um recebedor")
        UUID payeeId,

        @Min(value = 5, message = "o valor mínimo da transação é 5")
        Double amount
) {
}
