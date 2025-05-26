package com.pragma.challenge.technology_service.domain.model;

import com.pragma.challenge.technology_service.domain.validation.annotation.NotNull;
import com.pragma.challenge.technology_service.domain.validation.annotation.ValidList;
import java.util.List;

public record TechnologyIds(@NotNull @ValidList(min = 3, max = 20) List<Long> ids) {}
