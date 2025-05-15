package com.pragma.challenge.technology_service.infrastructure.adapters.persistence;

import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper.TechnologyEntityMapper;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyRepository;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.exceptions.standard_exception.BadRequest;
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
    log.info(
        "{} Saving technology with name: {} and description: {}.",
        LOG_PREFIX,
        technology.name(),
        technology.description());
    return technologyRepository
        .save(technologyEntityMapper.toEntity(technology))
        .map(technologyEntityMapper::toModel);
  }

  @Override
  public Mono<Boolean> existByName(String name) {
    return technologyRepository
        .existsByName(name)
        .flatMap(
            exist -> {
              if (Boolean.TRUE.equals(exist)) {
                return Mono.empty();
              }
              log.info("{} Technology name: {} already exist", LOG_PREFIX, name);
              return Mono.error(BadRequest::new);
            });
  }
}
