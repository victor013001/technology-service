package com.pragma.challenge.technology_service.infrastructure.entrypoints.util;

import com.pragma.challenge.technology_service.infrastructure.entrypoints.exceptions.standard_exception.BadRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestValidator {
  private static final String LOG_PREFIX = "[Request_Validator] >>> ";

  private final Validator validator;

  public <T> Mono<T> validate(T requestDto) {
    Set<ConstraintViolation<T>> violations = validator.validate(requestDto);
    if (!violations.isEmpty()) {
      List<String> errors = violations.stream().map(ConstraintViolation::getMessage).toList();
      log.info("{} Constraint violations: {}", LOG_PREFIX, errors);
      return Mono.error(BadRequest::new);
    }
    return Mono.just(requestDto);
  }
}
