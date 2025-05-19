package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper;

import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TechnologyProfileEntityMapper {
  TechnologyProfile toModel(TechnologyProfileEntity entity);

  TechnologyProfileEntity toEntity(TechnologyProfile technology);
}
