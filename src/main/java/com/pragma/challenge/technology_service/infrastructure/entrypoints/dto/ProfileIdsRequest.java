package com.pragma.challenge.technology_service.infrastructure.entrypoints.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ProfileIdsRequest(@NotNull(message = "The id list is mandatory.") List<String> ids) {}
