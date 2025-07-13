package com.project.MyBank.domain.dtos;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record PaymentRequestDto(
        @Email(message = "precisa do email do pagador")
        @NotBlank(message = "precisa ter um pagador")
        String payerEmail,

        @Email(message = "precisa do email do recebedor")
        @NotBlank(message = "precisa ter um recebedor")
        String payeeEmail,

        @NotBlank(message = "é preciso dá senha")
        @Size(min = 4, message = "a senha do pagador precisa ter pelo menos 4 caracteres")
        String payerPassword,

        @NotNull(message = "precisa ter um valor para transação")
        @Min(value = 5, message = "o valor mínimo da transação é 5")
        Double amount
) {
}
