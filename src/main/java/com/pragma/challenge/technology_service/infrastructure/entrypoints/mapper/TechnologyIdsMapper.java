package com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper;

import com.pragma.challenge.technology_service.domain.model.TechnologyIds;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyIdsDto;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyIdsRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TechnologyIdsMapper {
  TechnologyIdsDto toTechnologyIdsDto(TechnologyIdsRequest technologyIdsRequest);

  TechnologyIds toTechnologyIds(TechnologyIdsDto technologyIdsDto);
}
