package com.project.MyBank.domain.dtos;

import java.util.UUID;
import java.util.Set;

import com.project.MyBank.domain.Transaction;
import com.project.MyBank.domain.User;

public record UserWithTransactionDto(
    UUID userId,
    String name,
    String email,
    Double money,
    String imageUrl,
    Set<Transaction> payerTransactions,
    Set<Transaction> payeeTransactions
) {
    
    public UserWithTransactionDto(User user) {
        this(
            user.getUserId(),
            user.getName(),
            user.getEmail(),
            user.getMoney(),
            user.getImageUrl(),
            user.getPayerTransactions(),
            user.getPayeeTransactions()
        );
    }
}
