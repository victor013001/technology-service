package com.pragma.challenge.technology_service.infrastructure;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.pragma.challenge.technology_service.domain.constants.Constants;
import com.pragma.challenge.technology_service.domain.exceptions.StandardError;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.TechnologyHandler;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.SwaggerResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TechnologyRouterRestV1 {
  @Bean
  @RouterOperations({
    @RouterOperation(
        path = "/api/v1/technology",
        method = RequestMethod.POST,
        beanClass = TechnologyHandler.class,
        beanMethod = "createTechnology",
        operation =
            @Operation(
                operationId = "createTechnology",
                summary = "Create new technology",
                requestBody =
                    @RequestBody(
                        required = true,
                        content =
                            @Content(
                                mediaType = MediaType.APPLICATION_JSON_VALUE,
                                schema = @Schema(implementation = TechnologyDto.class))),
                responses = {
                  @ApiResponse(
                      responseCode = "201",
                      description = Constants.TECHNOLOGY_CREATED_MSG,
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema =
                                  @Schema(
                                      implementation =
                                          SwaggerResponses.DefaultMessageResponse.class))),
                  @ApiResponse(
                      responseCode = "400",
                      description = Constants.BAD_REQUEST_MSG,
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = StandardError.class))),
                  @ApiResponse(
                      responseCode = "409",
                      description = Constants.TECHNOLOGY_ALREADY_EXISTS_MSG,
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = StandardError.class))),
                  @ApiResponse(
                      responseCode = "500",
                      description = Constants.SERVER_ERROR_MSG,
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = StandardError.class)))
                })),
    @RouterOperation(
        path = "/api/v1/technology/exists",
        method = RequestMethod.GET,
        beanClass = TechnologyHandler.class,
        beanMethod = "technologiesExists",
        operation =
            @Operation(
                operationId = "technologiesExists",
                summary = "Check if technologies exist",
                parameters = {
                  @Parameter(
                      in = ParameterIn.QUERY,
                      name = "id",
                      description = "Technology IDs to check existence",
                      required = true,
                      array = @ArraySchema(schema = @Schema(type = "string", example = "1")))
                },
                responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "Existence check completed.",
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema =
                                  @Schema(
                                      implementation =
                                          SwaggerResponses.DefaultBooleanResponse.class))),
                  @ApiResponse(
                      responseCode = "400",
                      description = Constants.BAD_REQUEST_MSG,
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = StandardError.class))),
                  @ApiResponse(
                      responseCode = "500",
                      description = Constants.SERVER_ERROR_MSG,
                      content =
                          @Content(
                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                              schema = @Schema(implementation = StandardError.class)))
                })),
      @RouterOperation(
          path = "/api/v1/technology",
          method = RequestMethod.DELETE,
          beanClass = TechnologyHandler.class,
          beanMethod = "deleteTechnologies",
          operation =
          @Operation(
              operationId = "deleteTechnologies",
              summary = "Delete technologies by profile ids",
              parameters = {
                  @Parameter(
                      in = ParameterIn.QUERY,
                      name = "profileId",
                      description = "Profile IDs to delete technologies",
                      required = true,
                      array = @ArraySchema(schema = @Schema(type = "string", example = "1")))
              },
              responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = Constants.TECHNOLOGIES_DELETED_MSG,
                      content =
                      @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema =
                          @Schema(
                              implementation =
                                  SwaggerResponses.DefaultBooleanResponse.class))),
                  @ApiResponse(
                      responseCode = "400",
                      description = Constants.BAD_REQUEST_MSG,
                      content =
                      @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = StandardError.class))),
                  @ApiResponse(
                      responseCode = "500",
                      description = Constants.SERVER_ERROR_MSG,
                      content =
                      @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = StandardError.class)))
              })),
  })
  public RouterFunction<ServerResponse> routerFunction(TechnologyHandler technologyHandler) {
    return nest(
        path("/api/v1/technology"),
        route(RequestPredicates.POST(""), technologyHandler::createTechnology)
            .andRoute(RequestPredicates.GET("/exists"), technologyHandler::technologiesExists)
            .andRoute(RequestPredicates.POST("/profile"), technologyHandler::createRelation)
            .andRoute(RequestPredicates.GET(""), technologyHandler::getTechnologiesByProfileId)
            .andRoute(RequestPredicates.DELETE(""), technologyHandler::deleteTechnologies));
  }
}
