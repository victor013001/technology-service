package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository;

import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface TechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {
  Mono<Boolean> existsByName(String name);

  @Query("SELECT t.* FROM technology t JOIN technology_profile pt ON pt.technology_id = t.id WHERE pt.profile_id = :profileId")
  Flux<TechnologyEntity> findAllByProfileId(Long profileId);
}
