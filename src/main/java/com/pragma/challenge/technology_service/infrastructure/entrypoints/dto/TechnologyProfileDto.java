package com.pragma.challenge.technology_service.infrastructure.entrypoints.dto;

import java.util.List;

public record TechnologyProfileDto(List<TechnologyProfileRelationDto> relations) {}
