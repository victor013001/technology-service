package com.pragma.challenge.technology_service.infrastructure.entrypoints.mapper;

import com.pragma.challenge.technology_service.domain.exceptions.standard_exception.BadRequest;
import com.pragma.challenge.technology_service.domain.model.ProfileIds;
import com.pragma.challenge.technology_service.infrastructure.entrypoints.dto.ProfileIdsRequest;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileIdsMapper {
  ProfileIds toTechnologyIds(ProfileIdsRequest profileIdsRequest);

  @Named("parseLong")
  default Long parseLong(String id) {
    try {
      return Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new BadRequest();
    }
  }

  @Mapping(source = "list", target = "list", qualifiedByName = "parseLong")
  default List<Long> stringListToLongList(List<String> list) {
    return list.stream().map(this::parseLong).toList();
  }
}
