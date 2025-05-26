package com.pragma.challenge.technology_service;

import static com.pragma.challenge.technology_service.util.TechnologyDtoDataUtil.getInvalidTechnologyDto;
import static com.pragma.challenge.technology_service.util.TechnologyDtoDataUtil.getTechnologyDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pragma.challenge.technology_service.domain.constants.Constants;
import com.pragma.challenge.technology_service.domain.enums.ServerResponses;
import com.pragma.challenge.technology_service.domain.exceptions.StandardError;
import com.pragma.challenge.technology_service.domain.model.TechnologyNoDescription;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyEntity;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyProfileEntity;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyProfileRepository;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.repository.TechnologyRepository;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.DefaultServerResponse;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
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

  private Long techId1;
  private Long techId2;
  private Long techId3;

  @BeforeEach
  void setUp() {
    List<TechnologyEntity> savedTechnologies =
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
            .collectList()
            .block();

    technologyProfileRepository
        .saveAll(
            List.of(
                TechnologyProfileEntity.builder()
                    .profileId(1L)
                    .technologyId(savedTechnologies.get(0).getId())
                    .build(),
                TechnologyProfileEntity.builder()
                    .profileId(1L)
                    .technologyId(savedTechnologies.get(1).getId())
                    .build(),
                TechnologyProfileEntity.builder()
                    .profileId(2L)
                    .technologyId(savedTechnologies.get(1).getId())
                    .build(),
                TechnologyProfileEntity.builder()
                    .profileId(2L)
                    .technologyId(savedTechnologies.get(2).getId())
                    .build()))
        .blockLast();


    techId1 = savedTechnologies.get(0).getId();
    techId2 = savedTechnologies.get(1).getId();
    techId3 = savedTechnologies.get(2).getId();
  }

  @AfterEach
  void tearDown() {
    technologyProfileRepository.deleteAll().block();
    technologyRepository.deleteAll().block();
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
        .expectBody(
            new ParameterizedTypeReference<DefaultServerResponse<String, StandardError>>() {})
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
        .expectBody(
            new ParameterizedTypeReference<DefaultServerResponse<Object, StandardError>>() {})
        .consumeWith(
            exchangeResult -> {
              var response = exchangeResult.getResponseBody();
              assertNotNull(response);
              assertEquals(
                  ServerResponses.BAD_REQUEST.getMessage(), response.error().getDescription());
            });
  }

  @Test
  void technologiesExists() {
    String uri = String.format("%s/exists?id=%d&id=%d&id=%d", BASE_PATH, techId1, techId2, techId3);
    webTestClient
        .get()
        .uri(uri)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(
            new ParameterizedTypeReference<DefaultServerResponse<Boolean, StandardError>>() {})
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
                DefaultServerResponse<List<TechnologyNoDescription>, StandardError>>() {})
        .consumeWith(
            exchangeResult -> {
              var response = exchangeResult.getResponseBody();
              assertNotNull(response);
              assertEquals(2, response.data().size());
            });
  }

  @Test
  void deleteByProfileId() {
    webTestClient
        .delete()
        .uri(uriBuilder -> uriBuilder.path(BASE_PATH).queryParam("profileId", "1").build())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(
            new ParameterizedTypeReference<DefaultServerResponse<String, StandardError>>() {})
        .consumeWith(
            exchangeResult -> {
              var response = exchangeResult.getResponseBody();
              assertNotNull(response);
              assertEquals(Constants.TECHNOLOGIES_DELETED_MSG, response.data());
              assertEquals(
                  2,
                  Objects.requireNonNull(technologyRepository.findAll().collectList().block())
                      .size());
            });
  }
}
