package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateHistoryTwoWayConverter
    extends AbstractTwoWayConverter<StatesHistoryEntity, StateHistoryDTO> {

  @Autowired
  private ConverterService converterService;

  @Override
  public StateHistoryDTO entityToDto(StatesHistoryEntity source) {
    return StateHistoryDTO.builder()
        .id(source.getId())
        .candidateId(source.getId())
        .position(converterService.convert(source.getApplicationEntity().getPositionEntity(),
            PositionDTO.class))
        .channel(converterService.convert(source.getApplicationEntity().getChannelEntity(),
            ChannelDTO.class))
        .applicationDTO(
            converterService.convert(source.getApplicationEntity(), ApplicationDTO.class))
        .languageSkill(source.getLanguageSkill())
        .description(source.getDescription())
        .result(source.getResult())
        .offeredMoney(source.getOfferedMoney())
        .claim(source.getClaim())
        .feedbackDate(source.getFeedbackDate())
        .dayOfStart(source.getDayOfStart())
        .stateDTO(converterService.convert(source.getStatesEntity(), StateDTO.class))
        .creationDate(source.getCreationDate())
        .build();
  }

  @Override
  public StatesHistoryEntity dtoToEntity(StateHistoryDTO source) {
    return StatesHistoryEntity.builder()
        .id(source.getId())
        .applicationEntity(
            converterService.convert(source.getApplicationDTO(), ApplicationEntity.class))
        .creationDate(source.getCreationDate())
        .languageSkill(source.getLanguageSkill())
        .description(source.getDescription())
        .result(source.getResult())
        .offeredMoney(source.getOfferedMoney())
        .claim(source.getClaim())
        .feedbackDate(source.getFeedbackDate())
        .dayOfStart(source.getDayOfStart())
        .statesEntity(converterService.convert(source.getStateDTO(), StatesEntity.class))
        .build();
  }
}
