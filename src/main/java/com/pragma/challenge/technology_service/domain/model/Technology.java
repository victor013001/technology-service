package com.pragma.challenge.technology_service.domain.model;

import com.pragma.challenge.technology_service.domain.validation.annotation.NotNull;
import com.pragma.challenge.technology_service.domain.validation.annotation.Size;

public record Technology(Long id, @NotNull @Size(max = 50) String name, @NotNull @Size(max = 90) String description) {}
