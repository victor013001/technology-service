package com.pragma.challenge.technology_service.util;

import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;

public class TechnologyDtoDataUtil {
  private TechnologyDtoDataUtil() throws InstantiationException {
    throw new InstantiationException("Data class cannot be instantiated");
  }

  public static TechnologyDto getTechnologyDto() {
    return new TechnologyDto(
        "Java",
        "Object-oriented programming language and software platform used to develop applications");
  }

  public static TechnologyDto getInvalidTechnologyDto() {
    return new TechnologyDto(
        "Java",
        "Object-oriented programming language and software platform used to develop a wide variety of applications");
  }
}
