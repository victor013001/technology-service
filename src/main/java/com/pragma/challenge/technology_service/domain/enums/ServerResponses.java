package com.pragma.challenge.technology_service.domain.enums;

import com.pragma.challenge.technology_service.domain.constants.Constants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ServerResponses {
  BAD_REQUEST("E000", HttpStatus.BAD_REQUEST, Constants.BAD_REQUEST_MSG),
  SERVER_ERROR("E001", HttpStatus.INTERNAL_SERVER_ERROR, Constants.SERVER_ERROR_MSG),
  RESOURCE_NOT_FOUND("E002", HttpStatus.NOT_FOUND, Constants.RESOURCE_NOT_FOUND_MSG),
  TECHNOLOGY_CREATED("", HttpStatus.CREATED, Constants.TECHNOLOGY_CREATED_MSG),
  TECHNOLOGY_ALREADY_EXISTS("E003", HttpStatus.CONFLICT, Constants.TECHNOLOGY_ALREADY_EXISTS_MSG),
  TECHNOLOGY_NOT_FOUND("E004", HttpStatus.NOT_FOUND, Constants.TECHNOLOGY_NOT_FOUND_MSG),
  TECHNOLOGY_PROFILE_CREATED("", HttpStatus.CREATED, Constants.TECHNOLOGY_PROFILE_CREATED_MSG),
  TECHNOLOGIES_DELETED("", HttpStatus.OK, Constants.TECHNOLOGIES_DELETED_MSG);

  private final String code;
  private final HttpStatus httpStatus;
  private final String message;
}
