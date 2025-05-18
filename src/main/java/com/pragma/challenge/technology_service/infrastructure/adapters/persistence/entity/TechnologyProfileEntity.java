package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "technology_profile")
public class TechnologyProfileEntity {
  @Id
  private Long id;
  private Long technologyId;
  private Long profileId;
}
