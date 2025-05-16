package com.pragma.challenge.technology_service.domain.exceptions.standard_exception;

import com.pragma.challenge.technology_service.domain.enums.ServerResponses;
import com.pragma.challenge.technology_service.domain.exceptions.StandardException;

public class TechnologyAlreadyExists extends StandardException {
  public TechnologyAlreadyExists() {
    super(ServerResponses.TECHNOLOGY_ALREADY_EXISTS);
  }
}
