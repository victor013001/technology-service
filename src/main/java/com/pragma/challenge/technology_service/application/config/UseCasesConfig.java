package com.pragma.challenge.technology_service.application.config;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import com.pragma.challenge.technology_service.domain.usecase.TechnologyUseCase;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.TechnologyPersistenceAdapter;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper.TechnologyEntityMapper;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper.TechnologyProfileEntityMapper;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyProfileRepository;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCasesConfig {

  private final TechnologyRepository technologyRepository;
  private final TechnologyEntityMapper technologyEntityMapper;
  private final TechnologyProfileRepository technologyProfileRepository;
  private final TechnologyProfileEntityMapper technologyProfileEntityMapper;

  @Bean
  public TechnologyServicePort technologyServicePort(
      TechnologyPersistencePort technologyPersistencePort) {
    return new TechnologyUseCase(technologyPersistencePort);
  }

  @Bean
  public TechnologyPersistencePort technologyPersistencePort() {
    return new TechnologyPersistenceAdapter(
        technologyRepository,
        technologyEntityMapper,
        technologyProfileRepository,
        technologyProfileEntityMapper);
  }
}
