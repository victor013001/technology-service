package com.pragma.challenge.technology_service.domain.exceptions.standard_exception;

import com.pragma.challenge.technology_service.domain.enums.ServerResponses;
import com.pragma.challenge.technology_service.domain.exceptions.StandardException;

public class BadRequest extends StandardException {
  public BadRequest() {
    super(ServerResponses.BAD_REQUEST);
  }
}
