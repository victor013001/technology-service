package com.pragma.challenge.technology_service.infrastructure.adapters.persistence;

import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper.TechnologyEntityMapper;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements TechnologyPersistencePort {
  private static final String LOG_PREFIX = "[Technology_Persistence_Adapter] >>> ";

  private final TechnologyRepository technologyRepository;
  private final TechnologyEntityMapper technologyEntityMapper;

  @Override
  public Mono<Technology> save(Technology technology) {
    return technologyRepository
        .save(technologyEntityMapper.toEntity(technology))
        .map(technologyEntityMapper::toModel);
  }
}
