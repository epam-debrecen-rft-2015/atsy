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

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

  @Autowired
  private StatesHistoryService statesHistoryService;

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private ConverterService converterService;

  @Transactional(readOnly = true)
  @Override
  public ApplicationDTO getApplicationDtoById(Long applicationId) {
    Assert.notNull(applicationId);
    ApplicationEntity applicationEntity = applicationsRepository.findOne(applicationId);
    if (applicationEntity != null) {
      return converterService.convert(applicationEntity, ApplicationDTO.class);
    }
    return null;
  }

  @Transactional
  @Override
  public ApplicationDTO saveOrUpdate(ApplicationDTO applicationDTO) {
    Assert.notNull(applicationDTO);
    Assert.notNull(applicationDTO.getCandidateId());
    Assert.notNull(applicationDTO.getPositionId());
    Assert.notNull(applicationDTO.getChannelId());

    ApplicationEntity applicationEntity =
        converterService.convert(applicationDTO, ApplicationEntity.class);
    ApplicationEntity savedOrUpdateApplicationEntity = applicationsRepository.saveAndFlush(applicationEntity);

    return converterService.convert(savedOrUpdateApplicationEntity, ApplicationDTO.class);
  }

  @Transactional
  @Override
  public Long saveApplication(ApplicationDTO applicationDTO, StateHistoryDTO stateHistoryDTO) {
    Assert.notNull(stateHistoryDTO);
    
    ApplicationDTO savedOrUpdatedApplicationDto = saveOrUpdate(applicationDTO);
    stateHistoryDTO.setApplicationDTO(savedOrUpdatedApplicationDto);
    statesHistoryService.saveStateHistory(stateHistoryDTO);
    return savedOrUpdatedApplicationDto.getId();
  }

}
