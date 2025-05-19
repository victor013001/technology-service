package com.pragma.challenge.technology_service.domain.spi;

import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import reactor.core.publisher.Mono;

public interface TechnologyPersistencePort {
  Mono<Technology> save(Technology technology);

  Mono<Boolean> existsByName(String name);

  Mono<Boolean> existsById(Long id);

  Mono<TechnologyProfile> saveTechnologyProfile(
      TechnologyProfile technologyProfile);
}
