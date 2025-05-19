package com.pragma.challenge.technology_service.domain.usecase;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.domain.exceptions.standard_exception.TechnologyAlreadyExists;
import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.model.TechnologyIds;
import com.pragma.challenge.technology_service.domain.model.TechnologyNoDescription;
import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class TechnologyUseCase implements TechnologyServicePort {
  private static final String LOG_PREFIX = "[TECHNOLOGY_USE_CASE] >>>";

  private final TechnologyPersistencePort technologyPersistencePort;
  private final TransactionalOperator transactionalOperator;

  @Override
  public Mono<Technology> registerTechnology(Technology technology) {
    return technologyPersistencePort
        .existsByName(technology.name())
        .flatMap(
            exist -> {
              if (Boolean.TRUE.equals(exist)) {
                log.error("{} Technology name: {} already exist", LOG_PREFIX, technology.name());
                return Mono.error(TechnologyAlreadyExists::new);
              }
              return Mono.empty();
            })
        .then(Mono.defer(() -> technologyPersistencePort.save(technology)))
        .as(transactionalOperator::transactional);
  }

  @Override
  public Mono<Boolean> checkTechnologiesIds(TechnologyIds technologyIds) {
    return Flux.fromIterable(technologyIds.ids())
        .flatMap(
            id ->
                technologyPersistencePort
                    .existsById(id)
                    .flatMap(
                        exists -> {
                          if (Boolean.FALSE.equals(exists)) {
                            log.error("{} Technology with id: {} was not found", LOG_PREFIX, id);
                          }
                          return Mono.just(exists);
                        }))
        .any(result -> !result)
        .flatMap(foundFalse -> Mono.just(!foundFalse));
  }

  @Override
  public Mono<Void> registerTechnologyProfileRelation(List<TechnologyProfile> technologyProfiles) {
    return Flux.fromIterable(technologyProfiles)
        .flatMap(technologyPersistencePort::saveTechnologyProfile)
        .then()
        .as(transactionalOperator::transactional);
  }

  @Override
  public Mono<List<TechnologyNoDescription>> getProfileTechnologies(long profileId) {
    return technologyPersistencePort.findAllByProfileId(profileId);
  }
}
