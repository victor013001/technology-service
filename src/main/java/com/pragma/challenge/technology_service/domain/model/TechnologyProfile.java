package com.pragma.challenge.technology_service.domain.model;

import com.pragma.challenge.technology_service.domain.validation.annotation.NotNull;

public record TechnologyProfile(@NotNull Long technologyId, @NotNull Long profileId) {}
