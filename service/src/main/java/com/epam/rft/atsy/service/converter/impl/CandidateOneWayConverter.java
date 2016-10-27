package com.epam.rft.atsy.service.converter.impl;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.converter.AbstractOneWayConverter;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CandidateOneWayConverter
    extends AbstractOneWayConverter<CandidateEntity, CandidateDTO> {

  private final ApplicationsService applicationsService;

  private final PositionService positionService;

  public CandidateOneWayConverter(ApplicationsService applicationsService,
                                  PositionService positionService) {
    this.applicationsService = applicationsService;
    this.positionService = positionService;
  }

  @Override
  public CandidateDTO firstTypeToSecondType(CandidateEntity source) {

    Assert.notNull(source);

    CandidateDTO
        candidateDTO =
        CandidateDTO.builder()
            .id(source.getId())
            .name(source.getName())
            .email(source.getEmail())
            .phone(source.getPhone())
            .referer(source.getReferer())
            .languageSkill(source.getLanguageSkill())
            .description(source.getDescription())
            .cvFilename(source.getCvFilename())
            .deleted(source.isDeleted())
            .build();

    Set<String> positions = new HashSet<>();

    List<Long> positionIds =
        applicationsService.getApplicationsByCandidateDTO(candidateDTO).stream()
            .map(application -> application.getPositionId())
            .distinct()
            .collect(Collectors.toList());

    if (!positionIds.isEmpty()) {
      List<String>
          positionNames =
          positionService.getPositionsById(positionIds).stream()
              .map(position -> position.getName())
              .collect(Collectors.toList());
      positions.addAll(positionNames);
    }

    candidateDTO.setPositions(positions);

    return candidateDTO;
  }

}
