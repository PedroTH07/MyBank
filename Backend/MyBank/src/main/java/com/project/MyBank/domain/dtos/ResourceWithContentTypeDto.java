package com.project.MyBank.domain.dtos;

import org.springframework.core.io.Resource;

public record ResourceWithContentTypeDto(
        Resource resource,
        String contentType
) {
}
