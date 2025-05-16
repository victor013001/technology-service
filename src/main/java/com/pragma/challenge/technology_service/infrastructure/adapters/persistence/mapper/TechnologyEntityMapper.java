package com.pragma.challenge.technology_service.infrastructure.adapters.persistence.mapper;

import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.infrastructure.adapters.persistence.entity.TechnologyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TechnologyEntityMapper {
  Technology toModel(TechnologyEntity entity);

  TechnologyEntity toEntity(Technology technology);
}
