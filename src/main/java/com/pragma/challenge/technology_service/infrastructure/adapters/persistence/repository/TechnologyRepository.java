package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository;

import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {
  Mono<Boolean> existsByName(String name);
}
