package com.pragma.challenge.technology_service.domain.api;

import com.pragma.challenge.technology_service.domain.model.Technology;
import reactor.core.publisher.Mono;

public interface TechnologyServicePort {
  Mono<Technology> registerTechnology(Technology technology);
}
