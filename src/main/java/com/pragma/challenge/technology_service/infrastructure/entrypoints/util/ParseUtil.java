package com.pragma.challenge.technology_service.infrastructure.entrypoints.util;

import com.pragma.challenge.technology_service.domain.exceptions.standard_exception.BadRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParseUtil {

  public static long toLong(String value) {
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      throw new BadRequest();
    }
  }
}
