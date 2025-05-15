package com.pragma.challenge.technology_service.infrastructure.entrypoints.exceptions.standard_exception;

import com.pragma.challenge.technology_service.infrastructure.entrypoints.exceptions.StandardException;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.ServerResponses;

public class BadRequest extends StandardException {
  public BadRequest() {
    super(ServerResponses.BAD_REQUEST.getHttpStatus(), ServerResponses.BAD_REQUEST.getMessage());
  }
}
