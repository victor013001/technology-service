package com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.impl;

import static com.pragma.challenge.technology_service.infrastructure.entrypoints.util.ResponseUtil.buildResponse;
import static com.pragma.challenge.technology_service.infrastructure.entrypoints.util.ResponseUtil.buildStandardError;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.domain.constants.Constants;
import com.pragma.challenge.technology_service.domain.enums.ServerResponses;
import com.pragma.challenge.technology_service.domain.exceptions.StandardException;
import com.pragma.challenge.technology_service.domain.exceptions.standard_exception.BadRequest;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.ProfileIdsRequest;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyIdsRequest;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyProfileDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.TechnologyHandler;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.DefaultServerResponseMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.ProfileIdsMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyIdsMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyProfileMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.ParseUtil;
import java.util.function.Consumer;
import java.util.function.Function;
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
  private final TechnologyIdsMapper technologyIdsMapper;
  private final TechnologyProfileMapper technologyProfileMapper;
  private final DefaultServerResponseMapper defaultServerResponseMapper;
  private final ProfileIdsMapper profileIdsMapper;

  @Override
  public Mono<ServerResponse> createTechnology(ServerRequest request) {
    return request
        .bodyToMono(TechnologyDto.class)
        .switchIfEmpty(Mono.error(BadRequest::new))
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
                buildResponse(
                    ServerResponses.TECHNOLOGY_CREATED.getHttpStatus(),
                    ServerResponses.TECHNOLOGY_CREATED.getMessage(),
                    null,
                    defaultServerResponseMapper))
        .doOnError(logErrorHandler())
        .onErrorResume(StandardException.class, standardErrorHandler())
        .onErrorResume(genericErrorHandler());
  }

  @Override
  public Mono<ServerResponse> technologiesExists(ServerRequest request) {
    TechnologyIdsRequest technologyIdsRequest =
        new TechnologyIdsRequest(request.queryParams().get("id"));
    return Mono.just(technologyIdsMapper.toTechnologyIdsDto(technologyIdsRequest))
        .flatMap(
            technologyIdsDto -> {
              log.info(
                  "{} Checking if technologies with ids {} exist.",
                  LOG_PREFIX,
                  technologyIdsDto.ids());
              return technologyServicePort.checkTechnologiesIds(
                  technologyIdsMapper.toTechnologyIds(technologyIdsDto));
            })
        .flatMap(exists -> buildResponse(HttpStatus.OK, exists, null, defaultServerResponseMapper))
        .doOnError(logErrorHandler())
        .onErrorResume(StandardException.class, standardErrorHandler())
        .onErrorResume(genericErrorHandler());
  }

  @Override
  public Mono<ServerResponse> createRelation(ServerRequest request) {
    return request
        .bodyToMono(TechnologyProfileDto.class)
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
            buildResponse(
                ServerResponses.TECHNOLOGY_PROFILE_CREATED.getHttpStatus(),
                ServerResponses.TECHNOLOGY_PROFILE_CREATED.getMessage(),
                null,
                defaultServerResponseMapper))
        .doOnError(logErrorHandler())
        .onErrorResume(StandardException.class, standardErrorHandler())
        .onErrorResume(genericErrorHandler());
  }

  @Override
  public Mono<ServerResponse> getTechnologiesByProfileId(ServerRequest request) {
    long profileId = ParseUtil.toLong(request.queryParam(Constants.PROFILE_ID_PARAM).orElseThrow());
    log.info("{} Getting technologies for profile with id: {}", LOG_PREFIX, profileId);
    return technologyServicePort
        .getProfileTechnologies(profileId)
        .flatMap(
            technologies ->
                buildResponse(HttpStatus.OK, technologies, null, defaultServerResponseMapper))
        .doOnError(logErrorHandler())
        .onErrorResume(StandardException.class, standardErrorHandler())
        .onErrorResume(genericErrorHandler());
  }

  @Override
  public Mono<ServerResponse> deleteTechnologies(ServerRequest request) {
    ProfileIdsRequest idsRequest =
        new ProfileIdsRequest(request.queryParams().get(Constants.PROFILE_ID_PARAM));
    return Mono.just(idsRequest)
        .flatMap(
            profileIdsRequest -> {
              log.info(
                  "{} Deleting technologies for profiles ids: {}.",
                  LOG_PREFIX,
                  profileIdsRequest.ids());
              return technologyServicePort.deleteProfilesTechnologies(
                  profileIdsMapper.toTechnologyIds(profileIdsRequest));
            })
        .then(
            buildResponse(
                ServerResponses.TECHNOLOGIES_DELETED.getHttpStatus(),
                ServerResponses.TECHNOLOGIES_DELETED.getMessage(),
                null,
                defaultServerResponseMapper))
        .doOnError(logErrorHandler())
        .onErrorResume(StandardException.class, standardErrorHandler())
        .onErrorResume(genericErrorHandler());
  }

  private Consumer<Throwable> logErrorHandler() {
    return ex ->
        log.error(
            "{} Exception {} caught. Caused by: {}",
            LOG_PREFIX,
            ex.getClass().getSimpleName(),
            ex.getMessage());
  }

  private Function<StandardException, Mono<ServerResponse>> standardErrorHandler() {
    return ex ->
        buildResponse(ex.getHttpStatus(), null, ex.getStandardError(), defaultServerResponseMapper);
  }

  private Function<Throwable, Mono<ServerResponse>> genericErrorHandler() {
    return ex ->
        buildResponse(
            ServerResponses.SERVER_ERROR.getHttpStatus(),
            null,
            buildStandardError(ServerResponses.SERVER_ERROR),
            defaultServerResponseMapper);
  }
}
