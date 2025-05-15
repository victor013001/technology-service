package com.pragma.challenge.technology_service.domain.usecase;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyUseCase implements TechnologyServicePort {
  private static final String LOG_PREFIX = "[Technology_Use_Case] >>> ";

  private final TechnologyPersistencePort technologyPersistencePort;

  @Override
  public Mono<Technology> registerTechnology(Technology technology) {
    return technologyPersistencePort
        .existByName(technology.name())
        .then(technologyPersistencePort.save(technology));
  }
}
