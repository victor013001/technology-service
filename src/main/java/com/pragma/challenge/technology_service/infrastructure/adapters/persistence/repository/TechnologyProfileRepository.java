package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository;

import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TechnologyProfileRepository
    extends ReactiveCrudRepository<TechnologyProfileEntity, Long> {
  @Query(
      "SELECT COUNT(*) > 0 FROM technology_profile WHERE technology_id = :technologyId AND profile_id = :profileId")
  Mono<Boolean> existByTechnologyIdAndProfileId(Long technologyId, Long profileId);
}
