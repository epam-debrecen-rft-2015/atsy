package com.epam.rft.atsy.service.converter.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.service.converter.AbstractOneWayConverter;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class CandidateApplicationOneWayConverter
    extends AbstractOneWayConverter<ApplicationEntity, CandidateApplicationDTO> {

  private StatesHistoryRepository statesHistoryRepository;

  public CandidateApplicationOneWayConverter(
      StatesHistoryRepository statesHistoryRepository) {
    super();
    this.statesHistoryRepository = statesHistoryRepository;
  }

  @Override
  public CandidateApplicationDTO firstTypeToSecondType(ApplicationEntity source) {
    Assert.notNull(source);
    StatesHistoryEntity statesHistoryEntity =
        statesHistoryRepository
            .findTopByApplicationEntityOrderByCreationDateDesc(source);
    Assert.notNull(statesHistoryEntity);

    return CandidateApplicationDTO.builder()
        .lastStateId(statesHistoryEntity.getId())
        .id(source.getId())
        .name(source.getPositionEntity().getName())
        .creationDate(source.getCreationDate())
        .modificationDate(statesHistoryEntity.getCreationDate())
        .stateType(statesHistoryEntity.getStatesEntity().getName())
        .build();
  }

}
