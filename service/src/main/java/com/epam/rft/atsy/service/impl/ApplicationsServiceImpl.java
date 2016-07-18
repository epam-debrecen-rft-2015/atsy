package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

  @Resource
  private ModelMapper modelMapper;

  @Resource
  private StatesService statesService;

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ChannelRepository channelRepository;


  @Override
  public Long saveOrUpdate(ApplicationDTO applicationDTO) {
    Assert.notNull(applicationDTO);
    Assert.notNull(applicationDTO.getCandidateId());
    Assert.notNull(applicationDTO.getPositionId());
    Assert.notNull(applicationDTO.getChannelId());

    ApplicationEntity applicationEntity = modelMapper.map(applicationDTO, ApplicationEntity.class);
    applicationEntity
        .setCandidateEntity(candidateRepository.findOne(applicationDTO.getCandidateId()));
    applicationEntity.setPositionEntity(positionRepository.findOne(applicationDTO.getPositionId()));
    applicationEntity.setChannelEntity(channelRepository.findOne(applicationDTO.getChannelId()));
    return applicationsRepository.save(applicationEntity).getId();
  }

  @Transactional
  @Override
  public Long saveApplicaton(ApplicationDTO applicationDTO, StateDTO stateDTO) {
    Assert.notNull(stateDTO);

    Long applicationId = saveOrUpdate(applicationDTO);
    statesService.saveState(stateDTO, applicationId);
    return applicationId;
  }

}
