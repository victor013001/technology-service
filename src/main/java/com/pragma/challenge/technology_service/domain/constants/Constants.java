package com.pragma.challenge.technology_service.domain.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
  public static final String PROFILE_ID_PARAM = "profileId";
  public static final String BAD_REQUEST_MSG = "The request could not be processed due to invalid or incomplete data.";
  public static final String SERVER_ERROR_MSG = "An unexpected server error occurred. Please try again later.";
  public static final String RESOURCE_NOT_FOUND_MSG = "The requested resource was not found.";
  public static final String TECHNOLOGY_CREATED_MSG = "The technology was created successfully.";
  public static final String TECHNOLOGY_ALREADY_EXISTS_MSG = "The technology could not be created due to a data conflict.";
  public static final String TECHNOLOGY_NOT_FOUND_MSG = "The technology provided was not found.";
  public static final String TECHNOLOGY_PROFILE_CREATED_MSG = "Relations created successfully.";
  public static final String TECHNOLOGIES_DELETED_MSG = "The technologies were deleted successfully.";
}
