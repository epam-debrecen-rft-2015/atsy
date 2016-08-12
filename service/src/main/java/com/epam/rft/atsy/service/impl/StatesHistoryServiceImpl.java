package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class StatesHistoryServiceImpl implements StatesHistoryService {
  private final static Type
      STATE_HISTORY_VIEW_DTO_LIST_TYPE = new TypeToken<List<StateHistoryViewDTO>>() {
  }.getType();

  @Resource
  private ModelMapper modelMapper;

  @Autowired
  private StatesHistoryRepository statesHistoryRepository;

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private StatesRepository statesReporitory;

  @Transactional(readOnly = true)
  @Override
  public Collection<CandidateApplicationDTO> getCandidateApplicationsByCandidateIdOrderByModificationDateDesc(Long id) {
    Assert.notNull(id);
    CandidateEntity candidateEntity = candidateRepository.findOne(id);

    Assert.notNull(candidateEntity);
    List<ApplicationEntity> applicationList = applicationsRepository.findByCandidateEntity(candidateEntity);

    List<CandidateApplicationDTO> candidateApplicationDTOList = new LinkedList<>();
    for (ApplicationEntity applicationEntity : applicationList) {
      StatesHistoryEntity statesHistoryEntity =
          statesHistoryRepository.findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity);

      CandidateApplicationDTO candidateApplicationDTO = CandidateApplicationDTO.builder()
          .applicationId(applicationEntity.getId())
          .creationDate(applicationEntity.getCreationDate())
          .stateType(statesHistoryEntity.getStatesEntity().getName())
          .positionName(applicationEntity.getPositionEntity().getName())
          .lastStateId(statesHistoryEntity.getId())
          .modificationDate(statesHistoryEntity.getCreationDate()).build();

      candidateApplicationDTOList.add(candidateApplicationDTO);

    }
    return candidateApplicationDTOList.stream()
        .sorted((m1, m2) -> m2.getModificationDate().compareTo(m1.getModificationDate()))
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public Long saveStateHistory(StateHistoryDTO state, Long applicationId) {
    Assert.notNull(state);
    Assert.notNull(applicationId);
    Assert.notNull(state.getStateDTO());
    Assert.notNull(state.getStateDTO().getId());

    ApplicationEntity applicationEntity = applicationsRepository.findOne(applicationId);
    Assert.notNull(applicationEntity);

    Long stateId = state.getStateDTO().getId();
    StatesEntity statesEntity = statesReporitory.findOne(stateId);
    Assert.notNull(statesEntity);

    StatesHistoryEntity statesHistoryEntity = modelMapper.map(state, StatesHistoryEntity.class);
    statesHistoryEntity.setStatesEntity(statesEntity);
    statesHistoryEntity
        .setCreationDate(state.getCreationDate() == null ? new Date() : state.getCreationDate());
    statesHistoryEntity.setApplicationEntity(applicationEntity);

    return statesHistoryRepository.save(statesHistoryEntity).getId();
  }

  @Transactional(readOnly = true)
  @Override
  public List<StateHistoryViewDTO> getStateHistoriesByApplicationId(Long applicationId) {
    Assert.notNull(applicationId);
    ApplicationEntity applicationEntity = applicationsRepository.findOne(applicationId);

    Assert.notNull(applicationEntity);
    List<StatesHistoryEntity> statesHistoryEntities =
        statesHistoryRepository.findByApplicationEntityOrderByCreationDateDesc(applicationEntity);
    List<StateHistoryViewDTO> stateHistoryViewDTOs =
        modelMapper.map(statesHistoryEntities, STATE_HISTORY_VIEW_DTO_LIST_TYPE);

    Iterator<StateHistoryViewDTO> dtoIterator = stateHistoryViewDTOs.iterator();
    Iterator<StatesHistoryEntity> entityIterator = statesHistoryEntities.iterator();
    while (dtoIterator.hasNext()) {
      StateHistoryViewDTO stateHistoryViewDTO = dtoIterator.next();
      StatesHistoryEntity statesHistoryEntity = entityIterator.next();

      stateHistoryViewDTO
          .setCreationDate(statesHistoryEntity.getCreationDate());
      stateHistoryViewDTO
          .setApplicationDTO(modelMapper.map(applicationEntity, ApplicationDTO.class));
      stateHistoryViewDTO.setPosition(modelMapper.map(applicationEntity.getPositionEntity(),
          PositionDTO.class));
      stateHistoryViewDTO
          .setChannel(modelMapper.map(applicationEntity.getChannelEntity(), ChannelDTO.class));
      stateHistoryViewDTO
          .setStateDTO(modelMapper.map(statesHistoryEntity.getStatesEntity(), StateDTO.class));
    }

    return stateHistoryViewDTOs;
  }

}
