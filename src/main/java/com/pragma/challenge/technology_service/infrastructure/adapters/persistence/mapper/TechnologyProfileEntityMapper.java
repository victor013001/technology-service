package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper;

import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TechnologyProfileEntityMapper {
  TechnologyProfile toModel(TechnologyProfileEntity entity);

  @Mapping(target = "id", ignore = true)
  TechnologyProfileEntity toEntity(TechnologyProfile technology);
}
