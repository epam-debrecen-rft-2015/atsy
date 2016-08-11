package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;
import org.springframework.util.Assert;

public class StateHistoryViewTwoWayConverter
    extends AbstractTwoWayConverter<StatesHistoryEntity, StateHistoryViewDTO> {

  private ConverterService converterService;

  public StateHistoryViewTwoWayConverter(ConverterService converterService) {
    this.converterService = converterService;
  }

  @Override
  public StateHistoryViewDTO firstTypeToSecondType(StatesHistoryEntity source) {
    Assert.notNull(source);
    return StateHistoryViewDTO.builder()
        .id(source.getId())
        .candidateId(source.getId())
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
        .stateDTO(source.getStatesEntity() != null ?
            converterService.convert(source.getStatesEntity(), StateDTO.class) : null)
        .creationDate(source.getCreationDate())
        .build();
  }

  @Override
  public StatesHistoryEntity secondTypeToFirstType(StateHistoryViewDTO source) {
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
        .statesEntity(source.getStateDTO() != null ?
            converterService.convert(source.getStateDTO(), StatesEntity.class) : null)
        .build();
  }
}
