package com.pragma.challenge.technology_service.infrastructure.entrypoints.exceptions.standard_exception;

import com.pragma.challenge.technology_service.infrastructure.entrypoints.exceptions.StandardException;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.ServerResponses;

public class TechnologyAlreadyExists extends StandardException {
  public TechnologyAlreadyExists() {
    super(
        ServerResponses.TECHNOLOGY_ALREADY_EXISTS.getHttpStatus(),
        ServerResponses.TECHNOLOGY_ALREADY_EXISTS.getMessage());
  }
}
