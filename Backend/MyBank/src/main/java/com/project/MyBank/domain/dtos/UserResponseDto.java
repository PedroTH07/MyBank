package com.project.MyBank.domain.dtos;

import com.project.MyBank.domain.User;

import java.util.UUID;

public record UserResponseDto(
        UUID userId,
        String name,
        String email,
        Double money,
        String imageUrl
) {

    public UserResponseDto(User user) {
        this(user.getUserId(), user.getName(), user.getEmail(), user.getMoney(), user.getImageUrl());
    }
}
