package com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.impl;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.TechnologyHandler;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.RequestValidator;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.ServerResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final RequestValidator requestValidator;

  @Override
  public Mono<ServerResponse> createTechnology(ServerRequest request) {
    return request
        .bodyToMono(TechnologyDto.class)
        .flatMap(requestValidator::validate)
        .flatMap(
            technologyDto -> {
              log.info(
                  "{} Creating technology with name: {} and description: {}.",
                  LOG_PREFIX,
                  technologyDto.name(),
                  technologyDto.description());
              return technologyServicePort
                  .registerTechnology(technologyMapper.toTechnology(technologyDto))
                  .doOnSuccess(
                      technology ->
                          log.info(
                              "{} {} with name: {} and description: {}.",
                              LOG_PREFIX,
                              ServerResponses.TECHNOLOGY_CREATED.getMessage(),
                              technology.name(),
                              technology.description()));
            })
        .flatMap(
            ignore ->
                ServerResponse.status(ServerResponses.TECHNOLOGY_CREATED.getHttpStatus())
                    .bodyValue(ServerResponses.TECHNOLOGY_CREATED.getMessage()));
  }
}
