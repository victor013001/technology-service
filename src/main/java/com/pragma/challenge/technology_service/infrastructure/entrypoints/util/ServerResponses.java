package com.pragma.challenge.technology_service.infrastructure.entrypoints.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServerResponses {
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "Unable to process the request with the given data."),
  TECHNOLOGY_CREATED(HttpStatus.CREATED, "Technology created successfully."),
  SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred on the server.");

  private final HttpStatus httpStatus;
  private final String message;
}
