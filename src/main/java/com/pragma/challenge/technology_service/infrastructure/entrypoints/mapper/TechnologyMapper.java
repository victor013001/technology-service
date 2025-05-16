package com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper;

import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TechnologyMapper {
  @Mapping(target = "id", ignore = true)
  Technology toTechnology(TechnologyDto technologyDto);
}
