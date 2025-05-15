package com.pragma.challenge.technology_service.util;

import com.pragma.challenge.technology_service.domain.model.Technology;

public class TechnologyDataUtil {
  private TechnologyDataUtil() throws InstantiationException {
    throw new InstantiationException("Data class cannot be instantiated");
  }

  public static Technology getTechnologyWithoutId() {
    return new Technology(
        null,
        "Java",
        "Object-oriented programming language and software platform used to develop applications");
  }
}
