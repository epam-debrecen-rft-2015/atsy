package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class StateFlowTwoWayConverter
    extends AbstractTwoWayConverter<StateFlowEntity, StateFlowDTO> {

  @Autowired
  private ConverterService converterService;

  @Override
  public StateFlowDTO entityToDto(StateFlowEntity source) {
    return StateFlowDTO.builder()
        .fromStateDTO(converterService.convert(source.getFromStateEntity(), StateDTO.class))
        .toStateDTO(converterService.convert(source.getToStateEntity(), StateDTO.class))
        .build();
  }

  @Override
  public StateFlowEntity dtoToEntity(StateFlowDTO source) {
    return StateFlowEntity.builder()
        .fromStateEntity(converterService.convert(source.getFromStateDTO(), StatesEntity.class))
        .toStateEntity(converterService.convert(source.getToStateDTO(), StatesEntity.class))
        .build();
  }
}
