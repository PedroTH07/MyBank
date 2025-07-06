package com.project.MyBank.domain;

import java.util.UUID;

public record UserResponseDto(
        UUID userId,
        String name,
        String email,
        Double money
) {

    public UserResponseDto(User user) {
        this(user.getUserId(), user.getName(), user.getEmail(), user.getMoney());
    }
}
