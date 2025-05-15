package com.pragma.challenge.technology_service.infrastructure;

import com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.TechnologyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TechnologyRouterRestV1 {
  @Bean
  public RouterFunction<ServerResponse> routerFunction(TechnologyHandler technologyHandler) {
    return nest(
        path("/api/v1/technology"),
        route(RequestPredicates.POST("/"), technologyHandler::createTechnology));
  }
}
