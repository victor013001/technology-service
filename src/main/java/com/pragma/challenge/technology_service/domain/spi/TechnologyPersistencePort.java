package com.pragma.challenge.technology_service.domain.spi;

import com.pragma.challenge.technology_service.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface TechnologyPersistencePort {
  Mono<Technology> save(Technology technology);

  Mono<Void> validName(String name);
}
