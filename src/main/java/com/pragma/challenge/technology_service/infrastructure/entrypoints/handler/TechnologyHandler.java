package com.pragma.challenge.technology_service.infrastructure.entrypoints.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface TechnologyHandler {
  Mono<ServerResponse> createTechnology(ServerRequest request);

  Mono<ServerResponse> technologiesExists(ServerRequest request);

  Mono<ServerResponse> createRelation(ServerRequest request);

  Mono<ServerResponse> getTechnologiesByProfileId(ServerRequest request);
}
