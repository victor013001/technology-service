package com.pragma.challenge.technology_service.domain.exceptions;

import com.pragma.challenge.technology_service.domain.enums.ServerResponses;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StandardException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final StandardError standardError;

  public StandardException(ServerResponses serverResponses) {
    super(serverResponses.getMessage());
    this.httpStatus = serverResponses.getHttpStatus();
    this.standardError =
        StandardError.builder()
            .timestamp(LocalDateTime.now())
            .description(serverResponses.getMessage())
            .build();
  }
}
