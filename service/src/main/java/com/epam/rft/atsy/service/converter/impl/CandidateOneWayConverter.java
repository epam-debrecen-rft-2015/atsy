package com.epam.rft.atsy.service.converter.impl;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.converter.AbstractOneWayConverter;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ui2016 on 2016.08.23..
 */
public class CandidateOneWayConverter
    extends AbstractOneWayConverter<CandidateEntity, CandidateDTO> {

  @Autowired
  private ApplicationsService applicationsService;

  @Autowired
  private PositionService positionService;

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
            .build();

    Set<String> positions = new HashSet<>();

    List<ApplicationDTO>
        applicationDTOs =
        applicationsService.getApplicationsByCandidateDTO(candidateDTO);

    for (ApplicationDTO applicationDTO : applicationDTOs) {

      PositionDTO positionDTO = positionService.getPositionById(applicationDTO.getPositionId());

      positions.add(positionDTO.getName());

    }

    candidateDTO.setPositions(positions);

    return candidateDTO;
  }

}
