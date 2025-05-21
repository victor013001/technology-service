package com.pragma.challenge.technology_service.domain.api;

import com.pragma.challenge.technology_service.domain.model.ProfileIds;
import com.pragma.challenge.technology_service.domain.model.Technology;
import com.pragma.challenge.technology_service.domain.model.TechnologyIds;
import com.pragma.challenge.technology_service.domain.model.TechnologyNoDescription;
import com.pragma.challenge.technology_service.domain.model.TechnologyProfile;
import java.util.List;
import reactor.core.publisher.Mono;

public interface TechnologyServicePort {
  Mono<Technology> registerTechnology(Technology technology);

  Mono<Boolean> checkTechnologiesIds(TechnologyIds technologyIdsDto);

  Mono<Void> registerTechnologyProfileRelation(List<TechnologyProfile> technologyProfiles);

  Mono<List<TechnologyNoDescription>> getProfileTechnologies(long profileId);

  Mono<Void> deleteProfilesTechnologies(ProfileIds profileIds);
}
