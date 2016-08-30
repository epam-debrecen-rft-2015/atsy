package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryViewDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatesHistoryServiceImpl implements StatesHistoryService {

  @Autowired
  private ConverterService converterService;

  @Autowired
  private ApplicationsService applicationsService;

  @Autowired
  private StatesHistoryRepository statesHistoryRepository;

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private StatesRepository statesRepository;

  @Transactional(readOnly = true)
  @Override
  public Collection<CandidateApplicationDTO> getCandidateApplicationsByCandidateIdOrderByModificationDateDesc(
      Long id) {
    Assert.notNull(id);
    CandidateEntity candidateEntity = candidateRepository.findOne(id);

    Assert.notNull(candidateEntity);
    List<ApplicationEntity>
        applicationList =
        applicationsRepository.findByCandidateEntity(candidateEntity);

    List<CandidateApplicationDTO>
        candidateApplicationDTOs =
        converterService.convert(applicationList, CandidateApplicationDTO.class);

    return candidateApplicationDTOs.stream()
        .sorted((m1, m2) -> m2.getModificationDate().compareTo(m1.getModificationDate()))
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public Long saveStateHistory(StateHistoryDTO state) {
    Assert.notNull(state);
    Assert.notNull(state.getStateDTO());
    Assert.notNull(state.getStateDTO().getId());

    Long stateId = state.getStateDTO().getId();
    StatesEntity statesEntity = statesRepository.findOne(stateId);
    Assert.notNull(statesEntity);

    StatesHistoryEntity statesHistoryEntity = converterService.convert(state, StatesHistoryEntity.class);

    statesHistoryEntity.setCreationDate(state.getCreationDate() == null ? new Date() : state.getCreationDate());

    applicationsService.saveOrUpdate(state.getApplicationDTO());
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

    return converterService.convert(statesHistoryEntities, StateHistoryViewDTO.class);
  }
}
