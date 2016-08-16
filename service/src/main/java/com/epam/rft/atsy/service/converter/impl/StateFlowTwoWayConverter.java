package com.epam.rft.atsy.service.converter.impl;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.AbstractTwoWayConverter;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import org.springframework.util.Assert;

public class StateFlowTwoWayConverter
    extends AbstractTwoWayConverter<StateFlowEntity, StateFlowDTO> {

  private ConverterService converterService;

  public StateFlowTwoWayConverter(ConverterService converterService) {
    super();
    this.converterService = converterService;
  }

  @Override
  public StateFlowDTO firstTypeToSecondType(StateFlowEntity source) {
    Assert.notNull(source);
    return StateFlowDTO.builder()
        .fromStateDTO(source.getFromStateEntity() != null ? converterService
            .convert(source.getFromStateEntity(), StateDTO.class) : null)
        .toStateDTO(source.getToStateEntity() != null ? converterService
            .convert(source.getToStateEntity(), StateDTO.class) : null)
        .build();
  }

  @Override
  public StateFlowEntity secondTypeToFirstType(StateFlowDTO source) {
    Assert.notNull(source);
    return StateFlowEntity.builder()
        .fromStateEntity(source.getFromStateDTO() != null ? converterService
            .convert(source.getFromStateDTO(), StatesEntity.class) : null)
        .toStateEntity(source.getToStateDTO() != null ? converterService
            .convert(source.getToStateDTO(), StatesEntity.class) : null)
        .build();
  }
}
