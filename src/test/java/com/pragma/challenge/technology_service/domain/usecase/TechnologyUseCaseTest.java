package com.pragma.challenge.technology_service.domain.usecase;

import static com.pragma.challenge.technology_service.util.TechnologyDataUtil.getTechnologyWithoutId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pragma.challenge.technology_service.domain.exceptions.standard_exception.TechnologyAlreadyExists;
import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.spi.TechnologyPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {
  @InjectMocks TechnologyUseCase technologyUseCase;

  @Mock TechnologyPersistencePort technologyPersistencePort;
  @Mock TransactionalOperator transactionalOperator;

  @Test
  void shouldRegisterTechnologySuccessfully() {
    var technology = getTechnologyWithoutId();
    when(transactionalOperator.transactional(any(Mono.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(technologyPersistencePort.existsByName(technology.name())).thenReturn(Mono.empty());
    when(technologyPersistencePort.save(any(Technology.class)))
        .thenAnswer(
            invocationOnMock -> {
              Technology technologySaved = invocationOnMock.getArgument(0);
              return Mono.just(
                  new Technology(1L, technologySaved.name(), technologySaved.description()));
            });

    StepVerifier.create(technologyUseCase.registerTechnology(technology))
        .consumeNextWith(
            technologySaved -> {
              assertNotNull(technologySaved.id());
              assertEquals(technology.name(), technologySaved.name());
              assertEquals(technology.description(), technologySaved.description());
            })
        .verifyComplete();
  }

  @Test
  void shouldReturnTechnologyAlreadyExists() {
    var technology = getTechnologyWithoutId();
    when(transactionalOperator.transactional(any(Mono.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));
    when(technologyPersistencePort.existsByName(technology.name()))
        .thenReturn(Mono.error(TechnologyAlreadyExists::new));

    StepVerifier.create(technologyUseCase.registerTechnology(technology))
        .expectError(TechnologyAlreadyExists.class)
        .verify();
    verify(technologyPersistencePort, never()).save(any(Technology.class));
  }
}
