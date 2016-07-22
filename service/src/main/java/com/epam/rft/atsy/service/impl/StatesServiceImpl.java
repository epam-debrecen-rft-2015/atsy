package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateViewDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;

@Service
public class StatesServiceImpl implements StatesService {

  public static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";
  private final static Type STATEVIEWDTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {
  }.getType();
  @Resource
  private ModelMapper modelMapper;
  @Autowired
  private StatesRepository statesRepository;
  @Autowired
  private ApplicationsRepository applicationsRepository;
  @Autowired
  private CandidateRepository candidateRepository;

  @Override
  public Collection<CandidateApplicationDTO> getCandidateApplicationsByCandidateId(Long id) {
    Assert.notNull(id);
    CandidateEntity candidateEntity = candidateRepository.findOne(id);

    Assert.notNull(candidateEntity);
    List<ApplicationEntity>
        applicationList =
        applicationsRepository.findByCandidateEntity(candidateEntity);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

    List<CandidateApplicationDTO> candidateApplicationDTOList = new LinkedList<>();
    for (ApplicationEntity applicationEntity : applicationList) {
      StatesHistoryEntity
          statesHistoryEntity =
          statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);

      CandidateApplicationDTO candidateApplicationDTO = CandidateApplicationDTO.builder()
          .applicationId(applicationEntity.getId())
          .creationDate(simpleDateFormat.format(applicationEntity.getCreationDate()))
          .stateType(statesHistoryEntity.getStateType())
          .positionName(applicationEntity.getPositionEntity().getName())
          .lastStateId(statesHistoryEntity.getId())
          .modificationDate(simpleDateFormat.format(statesHistoryEntity.getCreationDate())).build();

      candidateApplicationDTOList.add(candidateApplicationDTO);

    }
    return candidateApplicationDTOList;
  }

  @Override
  public Long saveState(StateDTO state, Long applicationId) {
    Assert.notNull(state);
    Assert.notNull(applicationId);

    StatesHistoryEntity statesHistoryEntity = modelMapper.map(state, StatesHistoryEntity.class);

    statesHistoryEntity.setCreationDate(new Date());
    statesHistoryEntity.setApplicationEntity(applicationsRepository.findOne(applicationId));

    return statesRepository.save(statesHistoryEntity).getId();
  }


  @Override
  public List<StateViewDTO> getStatesByApplicationId(Long applicationId) {
    Assert.notNull(applicationId);
    ApplicationEntity applicationEntity = applicationsRepository.findOne(applicationId);

    Assert.notNull(applicationEntity);
    List<StatesHistoryEntity>
        stateEntities =
        statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity);
    List<StateViewDTO> stateDTOs = modelMapper.map(stateEntities, STATEVIEWDTO_LIST_TYPE);

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

    for (int i = 0; i < stateDTOs.size(); i++) {
      stateDTOs.get(i)
          .setCreationDate(simpleDateFormat.format(stateEntities.get(i).getCreationDate()));
      stateDTOs.get(i).setApplicationDTO(modelMapper.map(applicationEntity, ApplicationDTO.class));
      stateDTOs.get(i).setPosition(modelMapper.map(applicationEntity.getPositionEntity(),
          PositionDTO.class));
      stateDTOs.get(i).setChannel(modelMapper.map(applicationEntity.getChannelEntity(), ChannelDTO.class));
    }
    return stateDTOs;
  }
}
