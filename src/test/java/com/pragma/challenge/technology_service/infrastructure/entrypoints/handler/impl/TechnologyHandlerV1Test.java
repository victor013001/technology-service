package com.pragma.challenge.technology_service.infrastructure.entrypoints.handler.impl;

import static com.pragma.challenge.technology_service.util.TechnologyDtoDataUtil.getInvalidTechnologyDto;
import static com.pragma.challenge.technology_service.util.TechnologyDtoDataUtil.getTechnologyDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pragma.challenge.technology_service.domain.api.TechnologyServicePort;
import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.exceptions.standard_exception.BadRequest;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyMapper;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper.TechnologyMapperImpl;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.util.RequestValidator;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class TechnologyHandlerV1Test {

  @InjectMocks TechnologyHandlerV1 technologyHandler;

  @Mock TechnologyServicePort technologyServicePort;

  @Spy TechnologyMapper technologyMapper = new TechnologyMapperImpl();

  @Spy
  RequestValidator requestValidator =
      new RequestValidator(Validation.buildDefaultValidatorFactory().getValidator());

  @Test
  void shouldReturnCreatedResponse() {
    var technologyDto = getTechnologyDto();
    var request = MockServerRequest.builder().body(Mono.just(technologyDto));
    when(technologyServicePort.registerTechnology(any(Technology.class)))
        .thenAnswer(
            invocationOnMock -> {
              Technology technologySaved = invocationOnMock.getArgument(0);
              return Mono.just(
                  new Technology(1L, technologySaved.name(), technologySaved.description()));
            });
    StepVerifier.create(technologyHandler.createTechnology(request))
        .assertNext(
            serverResponse -> {
              assert serverResponse.statusCode().equals(HttpStatus.CREATED);
            })
        .verifyComplete();
  }

  @Test
  void shouldReturnMonoErrorWhenRequestInvalid() {
    var technologyDto = getInvalidTechnologyDto();
    var request = MockServerRequest.builder().body(Mono.just(technologyDto));
    StepVerifier.create(technologyHandler.createTechnology(request))
        .expectError(BadRequest.class)
        .verify();
  }
}
