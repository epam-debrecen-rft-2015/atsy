package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import javax.annotation.Resource;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

  @Resource
  private StatesHistoryService statesHistoryService;

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private ConverterService converterService;

  @Autowired
  private CandidateRepository candidateRepository;


  @Transactional(readOnly = true)
  @Override
  public List<ApplicationDTO> getApplicationsByCandidateDTO(CandidateDTO candidateDTO) {

    Assert.notNull(candidateDTO);
    Assert.notNull(candidateDTO.getId());

    CandidateEntity candidateEntity = candidateRepository.findOne(candidateDTO.getId());

    List<ApplicationEntity>
        applicationEntities =
        applicationsRepository.findByCandidateEntity(candidateEntity);

    return converterService.convert(applicationEntities, ApplicationDTO.class);
  }

  @Transactional
  @Override
  public Long saveOrUpdate(ApplicationDTO applicationDTO) {
    Assert.notNull(applicationDTO);
    Assert.notNull(applicationDTO.getCandidateId());
    Assert.notNull(applicationDTO.getPositionId());
    Assert.notNull(applicationDTO.getChannelId());

    ApplicationEntity
        applicationEntity =
        converterService.convert(applicationDTO, ApplicationEntity.class);

    return applicationsRepository.save(applicationEntity).getId();
  }

  @Transactional
  @Override
  public Long saveApplication(ApplicationDTO applicationDTO, StateHistoryDTO stateHistoryDTO) {
    Assert.notNull(stateHistoryDTO);

    Long applicationId = saveOrUpdate(applicationDTO);
    statesHistoryService.saveStateHistory(stateHistoryDTO, applicationId);
    return applicationId;
  }

}
