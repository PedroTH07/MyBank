package com.project.MyBank.domain;

public record UserRequestDto(
        String name,
        String email,
        String password
) {
}
