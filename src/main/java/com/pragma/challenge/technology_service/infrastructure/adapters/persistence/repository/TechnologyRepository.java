package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository;

import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends ReactiveCrudRepository<TechnologyEntity, Long> {}
