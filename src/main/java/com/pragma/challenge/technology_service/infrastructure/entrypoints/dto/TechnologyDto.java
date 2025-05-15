package com.pragma.challenge.technology_service.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechnologyDto(
    @NotBlank(message = "Name is mandatory")
        @Size(max = 50, message = "Name exceeds the permitted limit")
        String name,
    @NotBlank(message = "Description is mandatory")
        @Size(max = 90, message = "Name exceeds the permitted limit")
        String description) {}
