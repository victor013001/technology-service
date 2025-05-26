package com.pragma.challenge.technology_service.infrastructure.entrypoints.dto;

public record DefaultServerResponse<T, E>(T data, E error) {}
