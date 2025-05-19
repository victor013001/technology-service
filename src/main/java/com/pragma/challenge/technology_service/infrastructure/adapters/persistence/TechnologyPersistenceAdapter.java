package com.pragma.challenge.technology_service.infrastructure.adapters.persistence;

import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.model.TechnologyNoDescription;
import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper.TechnologyEntityMapper;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper.TechnologyProfileEntityMapper;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyProfileRepository;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyPersistenceAdapter implements TechnologyPersistencePort {
  private static final String LOG_PREFIX = "[TECHNOLOGY_PERSISTENCE_ADAPTER] >>>";

  private final TechnologyRepository technologyRepository;
  private final TechnologyEntityMapper technologyEntityMapper;
  private final TechnologyProfileRepository technologyProfileRepository;
  private final TechnologyProfileEntityMapper technologyProfileEntityMapper;

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
  public Mono<Boolean> existsByName(String name) {
    log.info("{} Checking if technology with name: {} exists.", LOG_PREFIX, name);
    return technologyRepository.existsByName(name);
  }

  @Override
  public Mono<Boolean> existsById(Long id) {
    log.info("{} Checking if technology with id: {} exists.", LOG_PREFIX, id);
    return technologyRepository.existsById(id);
  }

  @Override
  public Mono<TechnologyProfile> saveTechnologyProfile(TechnologyProfile technologyProfile) {
    log.info(
        "{} Saving relation with technology id: {} and profile id: {}",
        LOG_PREFIX,
        technologyProfile.technologyId(),
        technologyProfile.technologyId());
    return technologyProfileRepository
        .save(technologyProfileEntityMapper.toEntity(technologyProfile))
        .map(technologyProfileEntityMapper::toModel);
  }

  @Override
  public Mono<List<TechnologyNoDescription>> findAllByProfileId(long profileId) {
    log.info("{} Finding technologies for profile with id: {}", LOG_PREFIX, profileId);
    return technologyRepository
        .findAllByProfileId(profileId)
        .map(technologyEntityMapper::toTechnologyNoDescription)
        .collectList();
  }
}
