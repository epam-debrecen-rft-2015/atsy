package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StatesHistoryService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.service.response.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

  @Autowired
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

  @Transactional(readOnly = true)
  @Override
  public PagingResponse<CandidateApplicationDTO> getApplicationsByCandidateId(
      Long candidateId,
      int page,
      int size) {
    Assert.notNull(candidateId);

    CandidateEntity candidateEntity = candidateRepository.findOne(candidateId);

    Pageable pageRequest = new PageRequest(page, size);

    Page<ApplicationEntity>
        pageResult =
        applicationsRepository.findByCandidateEntity(candidateEntity, pageRequest);

    List<ApplicationEntity> applicationEntities = pageResult.getContent();

    List<CandidateApplicationDTO>
        candidateApplicationDTOs =
        converterService.convert(applicationEntities, CandidateApplicationDTO.class);

    candidateApplicationDTOs = candidateApplicationDTOs.stream()
        .sorted((m1, m2) -> m2.getModificationDate().compareTo(m1.getModificationDate()))
        .collect(Collectors.toList());

    Long total = pageResult.getTotalElements();

    PagingResponse<CandidateApplicationDTO>
        response =
        new PagingResponse<>(total, candidateApplicationDTOs);

    return response;
  }

  @Transactional
  @Override
  public void deleteApplicationsByCandidateDTO(CandidateDTO candidateDTO) {
    Assert.notNull(candidateDTO);
    Assert.notNull(candidateDTO.getId());

    List<ApplicationEntity>
        applicationEntities =
        converterService
            .convert(getApplicationsByCandidateDTO(candidateDTO), ApplicationEntity.class);

    for (ApplicationEntity applicationEntity : applicationEntities) {
      statesHistoryService.deleteStateHistoriesByApplication(
          converterService.convert(applicationEntity, ApplicationDTO.class));
      applicationsRepository.delete(applicationEntity);
    }
  }

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
    ApplicationEntity
        savedOrUpdateApplicationEntity =
        applicationsRepository.saveAndFlush(applicationEntity);

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