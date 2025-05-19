package com.pragma.challenge.technology_service;

import static com.pragma.challenge.technology_service.util.TechnologyDtoDataUtil.getInvalidTechnologyDto;
import static com.pragma.challenge.technology_service.util.TechnologyDtoDataUtil.getTechnologyDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pragma.challenge.technology_service.domain.enums.ServerResponses;
import com.pragma.challenge.technology_service.domain.exceptions.StandardError;
import com.pragma.challenge.technology_service.domain.model.TechnologyNoDescription;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyEntity;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyProfileEntity;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyProfileRepository;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyRepository;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.DefaultServerResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
@Import(TestcontainersConfiguration.class)
public class TechnologyRouterRestIT {

  private final String BASE_PATH = "/api/v1/technology";

  @Autowired WebTestClient webTestClient;
  @Autowired TechnologyRepository technologyRepository;
  @Autowired TechnologyProfileRepository technologyProfileRepository;

  @BeforeEach
  void setUp() {
    technologyRepository
        .saveAll(
            List.of(
                TechnologyEntity.builder()
                    .name("Node.js")
                    .description("Open-source, cross-platform JavaScript runtime environment")
                    .build(),
                TechnologyEntity.builder()
                    .name("React")
                    .description(
                        "JavaScript library used for building particularly single-page applications")
                    .build(),
                TechnologyEntity.builder()
                    .name("Spring Boot")
                    .description(
                        "Framework for building production-ready Java applications with minimal configuration")
                    .build()))
        .blockLast();
    technologyProfileRepository
        .saveAll(
            List.of(
                TechnologyProfileEntity.builder().profileId(1L).technologyId(1L).build(),
                TechnologyProfileEntity.builder().profileId(1L).technologyId(2L).build()))
        .blockLast();
  }

  @Test
  void createTechnology() {
    webTestClient
        .post()
        .uri(BASE_PATH)
        .bodyValue(getTechnologyDto())
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(DefaultServerResponse.class)
        .consumeWith(
            exchangeResult -> {
              var response = exchangeResult.getResponseBody();
              assertNotNull(response);
              assertEquals(ServerResponses.TECHNOLOGY_CREATED.getMessage(), response.data());
            });
  }

  @Test
  void createTechnologyBadRequest() {
    webTestClient
        .post()
        .uri(BASE_PATH)
        .bodyValue(getInvalidTechnologyDto())
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody(StandardError.class)
        .consumeWith(
            exchangeResult -> {
              var error = exchangeResult.getResponseBody();
              assertNotNull(error);
              assertEquals(ServerResponses.BAD_REQUEST.getMessage(), error.getDescription());
            });
  }

  @Test
  void technologiesExists() {
    webTestClient
        .get()
        .uri(BASE_PATH + "/exists?id=1&id=2&id=3")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(new ParameterizedTypeReference<DefaultServerResponse<Boolean>>() {})
        .consumeWith(
            exchangeResult -> {
              var response = exchangeResult.getResponseBody();
              assertNotNull(response);
              assertTrue(response.data());
            });
  }

  @Test
  void findTechnologiesByProfileId() {
    webTestClient
        .get()
        .uri(uriBuilder -> uriBuilder.path(BASE_PATH).queryParam("profileId", "1").build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(
            new ParameterizedTypeReference<
                DefaultServerResponse<List<TechnologyNoDescription>>>() {})
        .consumeWith(
            exchangeResult -> {
              var response = exchangeResult.getResponseBody();
              assertNotNull(response);
              assertEquals(2, response.data().size());
              System.out.println(response.data());
            });
  }
}
