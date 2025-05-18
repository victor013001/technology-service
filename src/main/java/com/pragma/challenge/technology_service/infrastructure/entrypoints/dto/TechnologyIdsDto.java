package com.pragma.challenge.technology_service.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record TechnologyIdsDto(
    @NotNull(message = "The id list is mandatory.")
        @Size(min = 3, max = 20, message = "The id list must contain between 3 and 20 ids.")
        List<Long> ids) {}
