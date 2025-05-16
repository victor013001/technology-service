package com.pragma.challenge.technology_service;

import org.springframework.boot.SpringApplication;

public class TestArchetypeGradleApplication {
  public static void main(String[] args) {
    SpringApplication.from(TechnologyServiceApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
