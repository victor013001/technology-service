package com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper;

import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.TechnologyProfileRelationDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TechnologyProfileMapper {
  List<TechnologyProfile> toTechnologyProfileRelation(
      List<TechnologyProfileRelationDto> technologyProfileRelationDto);
}
