package com.epam.rft.atsy.service.converter.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.AbstractTwoWayConverter;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.springframework.util.Assert;

public class StateHistoryTwoWayConverter
    extends AbstractTwoWayConverter<StatesHistoryEntity, StateHistoryDTO> {

  private ConverterService converterService;

  public StateHistoryTwoWayConverter(ConverterService converterService) {
    super();
    this.converterService = converterService;
  }

  @Override
  public StateHistoryDTO firstTypeToSecondType(StatesHistoryEntity source) {
    Assert.notNull(source);
    return StateHistoryDTO.builder()
        .id(source.getId())
        .candidateId(source.getApplicationEntity().getCandidateEntity().getId())
        .position(source.getApplicationEntity().getPositionEntity() != null ?
            converterService.convert(source.getApplicationEntity().getPositionEntity(),
                PositionDTO.class) : null)
        .channel(source.getApplicationEntity().getChannelEntity() != null ?
            converterService.convert(source.getApplicationEntity().getChannelEntity(),
                ChannelDTO.class) : null)
        .applicationDTO(source.getApplicationEntity() != null ?
            converterService.convert(source.getApplicationEntity(), ApplicationDTO.class) : null)
        .languageSkill(source.getLanguageSkill())
        .description(source.getDescription())
        .result(source.getResult())
        .offeredMoney(source.getOfferedMoney())
        .claim(source.getClaim())
        .feedbackDate(source.getFeedbackDate())
        .dayOfStart(source.getDayOfStart())
        .dateOfEnter(source.getDateOfEnter())
        .stateDTO(source.getStatesEntity() != null ?
            converterService.convert(source.getStatesEntity(), StateDTO.class) : null)
        .creationDate(source.getCreationDate())
        .recommendation(source.getRecommendation())
        .reviewerName(source.getReviewerName())
        .recommendedPositionLevel(source.getRecommendedPositionLevel())
        .build();
  }

  @Override
  public StatesHistoryEntity secondTypeToFirstType(StateHistoryDTO source) {
    Assert.notNull(source);
    return StatesHistoryEntity.builder()
        .id(source.getId())
        .applicationEntity(source.getApplicationDTO() != null ?
            converterService.convert(source.getApplicationDTO(), ApplicationEntity.class) : null)
        .creationDate(source.getCreationDate())
        .languageSkill(source.getLanguageSkill())
        .description(source.getDescription())
        .result(source.getResult())
        .offeredMoney(source.getOfferedMoney())
        .claim(source.getClaim())
        .feedbackDate(source.getFeedbackDate())
        .dayOfStart(source.getDayOfStart())
        .dateOfEnter(source.getDateOfEnter())
        .recommendation(source.getRecommendation())
        .reviewerName(source.getReviewerName())
        .recommendedPositionLevel(source.getRecommendedPositionLevel())
        .statesEntity(source.getStateDTO() != null ?
            converterService.convert(source.getStateDTO(), StatesEntity.class) : null)
        .build();
  }
}
