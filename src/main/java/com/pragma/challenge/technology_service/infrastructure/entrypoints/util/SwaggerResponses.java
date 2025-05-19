package com.pragma.challenge.technology_service.infrastructure.entrypoints.util;

import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

public final class SwaggerResponses {

  private SwaggerResponses() throws InstantiationException {
    throw new InstantiationException("Utility class");
  }

  @Data
  @Schema(name = "DefaultBooleanResponse")
  @AllArgsConstructor
  public static class DefaultBooleanResponse {
    private Boolean data;
  }

  @Data
  @Schema(name = "DefaultTechnologyDtoResponse")
  @AllArgsConstructor
  public static class DefaultTechnologyDtoResponse {
    private TechnologyDto data;
  }

  @Data
  @Schema(name = "DefaultMessageResponse")
  @AllArgsConstructor
  public static class DefaultMessageResponse {
    private String data;
  }
}
