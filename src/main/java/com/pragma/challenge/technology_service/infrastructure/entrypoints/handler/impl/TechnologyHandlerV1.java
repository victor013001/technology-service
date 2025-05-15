package com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.impl;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.TechnologyHandler;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyHandlerV1 implements TechnologyHandler {
  private static final String LOG_PREFIX = "[Technology_Handler_V1] >>> ";

  private final TechnologyServicePort technologyServicePort;
  private final TechnologyMapper technologyMapper;

  @Override
  public Mono<ServerResponse> createTechnology(ServerRequest request) {
    return request
        .bodyToMono(TechnologyDto.class)
        .flatMap(
            technologyDto ->
                technologyServicePort.registerTechnology(
                    technologyMapper.toTechnology(technologyDto)))
        .flatMap(technology -> ServerResponse.status(HttpStatus.CREATED).bodyValue(technology));
  }
}
