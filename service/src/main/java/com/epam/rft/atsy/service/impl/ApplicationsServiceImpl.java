package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

  @Resource
  private StatesHistoryService statesHistoryService;

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private ConverterService converterService;

  @Override
  public ApplicationDTO getApplication(Long applicationId) {
    Assert.notNull(applicationId);

    ApplicationDTO
        applicationDTO =
        converterService
            .convert(applicationsRepository.findOne(applicationId), ApplicationDTO.class);

    return applicationDTO;

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
