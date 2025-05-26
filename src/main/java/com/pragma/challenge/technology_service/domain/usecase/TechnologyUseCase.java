package com.pragma.challenge.technology_service.domain.usecase;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.domain.exceptions.standard_exception.TechnologyAlreadyExists;
import com.pragma.challenge.technology_service.domain.model.ProfileIds;
import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.model.TechnologyIds;
import com.pragma.challenge.technology_service.domain.model.TechnologyNoDescription;
import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import com.pragma.challenge.technology_service.domain.validation.ValidNotNull;
import com.pragma.challenge.technology_service.domain.validation.ValidSize;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class TechnologyUseCase implements TechnologyServicePort {
  private static final String LOG_PREFIX = "[TECHNOLOGY_USE_CASE] >>>";

  private final TechnologyPersistencePort technologyPersistencePort;

  @Override
  public Mono<Technology> registerTechnology(Technology technology) {
    return Mono.just(technology)
        .flatMap(
            technology1 -> {
              ValidNotNull.valid(technology);
              ValidSize.valid(technology);
              return Mono.just(technology);
            })
        .flatMap(technology1 -> technologyPersistencePort.existsByName(technology.name()))
        .flatMap(
            exist -> {
              if (Boolean.TRUE.equals(exist)) {
                log.error("{} Technology name: {} already exist", LOG_PREFIX, technology.name());
                return Mono.error(TechnologyAlreadyExists::new);
              }
              return Mono.empty();
            })
        .then(Mono.defer(() -> technologyPersistencePort.save(technology)));
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
        .then();
  }

  @Override
  public Mono<List<TechnologyNoDescription>> getProfileTechnologies(long profileId) {
    return technologyPersistencePort.findAllByProfileId(profileId);
  }

  @Override
  public Mono<Void> deleteProfilesTechnologies(ProfileIds profileIds) {
    return Flux.fromIterable(profileIds.ids())
        .flatMap(
            profileId ->
                technologyPersistencePort
                    .findTechnologyIdsByOnlyProfileId(profileId)
                    .flatMap(
                        technologyId ->
                            technologyPersistencePort
                                .deleteRelationByProfileId(profileId)
                                .then(
                                    technologyPersistencePort.deleteTechnologiesByIds(
                                        technologyId))))
        .then();
  }
}
