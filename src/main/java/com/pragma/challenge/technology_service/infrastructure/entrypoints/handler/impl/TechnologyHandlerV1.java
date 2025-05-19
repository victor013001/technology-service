package com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.impl;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.domain.enums.ServerResponses;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyIdsRequest;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyProfileDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.TechnologyHandler;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.DefaultServerResponseMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyIdsMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyProfileMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.RequestValidator;
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
  private static final String LOG_PREFIX = "[TECHNOLOGY_HANDLER_V1] >>>";

  private final TechnologyServicePort technologyServicePort;
  private final TechnologyMapper technologyMapper;
  private final RequestValidator requestValidator;
  private final TechnologyIdsMapper technologyIdsMapper;
  private final TechnologyProfileMapper technologyProfileMapper;
  private final DefaultServerResponseMapper defaultServerResponseMapper;

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
                    .bodyValue(
                        defaultServerResponseMapper.toResponse(
                            ServerResponses.TECHNOLOGY_CREATED.getMessage())));
  }

  @Override
  public Mono<ServerResponse> technologiesExists(ServerRequest request) {
    TechnologyIdsRequest technologyIdsRequest =
        new TechnologyIdsRequest(request.queryParams().get("id"));
    return Mono.just(technologyIdsMapper.toTechnologyIdsDto(technologyIdsRequest))
        .flatMap(requestValidator::validate)
        .flatMap(
            technologyIdsDto -> {
              log.info(
                  "{} Checking if technologies with ids {} exist.",
                  LOG_PREFIX,
                  technologyIdsDto.ids());
              return technologyServicePort.checkTechnologiesIds(
                  technologyIdsMapper.toTechnologyIds(technologyIdsDto));
            })
        .flatMap(
            exists ->
                ServerResponse.status(HttpStatus.OK)
                    .bodyValue(defaultServerResponseMapper.toResponse(exists)));
  }

  @Override
  public Mono<ServerResponse> createRelation(ServerRequest request) {
    return request
        .bodyToMono(TechnologyProfileDto.class)
        .flatMap(requestValidator::validate)
        .flatMap(
            technologyProfileDto -> {
              log.info("{} Creating technology profile relations", LOG_PREFIX);
              return technologyServicePort
                  .registerTechnologyProfileRelation(
                      technologyProfileMapper.toTechnologyProfileRelation(
                          technologyProfileDto.relations()))
                  .doOnSuccess(
                      technology -> log.info("{} Relations created successfully.", LOG_PREFIX));
            })
        .then(
            ServerResponse.status(ServerResponses.TECHNOLOGY_PROFILE_CREATED.getHttpStatus())
                .bodyValue(
                    defaultServerResponseMapper.toResponse(
                        ServerResponses.TECHNOLOGY_PROFILE_CREATED.getMessage())));
  }
}
