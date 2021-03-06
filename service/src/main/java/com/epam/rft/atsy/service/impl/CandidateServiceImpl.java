package com.epam.rft.atsy.service.impl;

import com.google.common.base.MoreObjects;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.request.CandidateFilterRequest;
import com.epam.rft.atsy.service.response.PagingResponse;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CandidateServiceImpl
    extends AbstractLogicallyDeletableService<CandidateDTO, CandidateEntity>
    implements CandidateService {

  @Autowired
  public CandidateServiceImpl(CandidateRepository candidateRepository,
                              ApplicationsRepository applicationsRepository,
                              ApplicationsService applicationsService,
                              ConverterService converterService) {
    super(CandidateDTO.class, candidateRepository, converterService);
    this.candidateRepository = candidateRepository;
    this.applicationsRepository = applicationsRepository;
    this.applicationsService = applicationsService;
  }

  private CandidateRepository candidateRepository;

  private ApplicationsService applicationsService;

  private ApplicationsRepository applicationsRepository;

  @Transactional(readOnly = true)
  @Override
  public CandidateDTO getCandidate(Long id) {
    Assert.notNull(id);

    CandidateEntity candidateEntity = candidateRepository.findOne(id);
    if (candidateEntity != null && !candidateEntity.isDeleted()) {
      return converterService.convert(candidateEntity, CandidateDTO.class);
    }
    return null;
  }

  @Transactional(readOnly = true)
  @Override
  public CandidateDTO getCandidateByApplicationID(Long applicationID) {
    Assert.notNull(applicationID);

    ApplicationEntity applicationEntity =
        applicationsRepository.findOne(applicationID);

    CandidateDTO
        candidateDTO =
        (applicationEntity != null ? converterService
            .convert(applicationEntity.getCandidateEntity(), CandidateDTO.class) : null);

    return candidateDTO;
  }

  @Transactional(readOnly = true)
  @Override
  public PagingResponse<CandidateDTO> getCandidatesByFilterRequest(
      CandidateFilterRequest candidateFilterRequest) {

    PageRequest pageRequest;

    validateCandidateFilterRequest(candidateFilterRequest);

    if (candidateFilterRequest.getSortName() != null
        && candidateFilterRequest.getSortOrder() != null) {

      Sort.Direction
          sortDirection =
          Sort.Direction.fromString(candidateFilterRequest.getSortOrder());

      pageRequest =
          new PageRequest(candidateFilterRequest.getPageNumber(),
              candidateFilterRequest.getPageSize(),
              sortDirection, candidateFilterRequest.getSortName());

    } else {
      pageRequest =
          new PageRequest(candidateFilterRequest.getPageNumber(),
              candidateFilterRequest.getPageSize());
    }

    final String
        name =
        MoreObjects.firstNonNull(candidateFilterRequest.getCandidateName(), StringUtils.EMPTY);
    final String
        email =
        MoreObjects.firstNonNull(candidateFilterRequest.getCandidateEmail(), StringUtils.EMPTY);
    final String
        phone =
        MoreObjects.firstNonNull(candidateFilterRequest.getCandidatePhone(), StringUtils.EMPTY);
    final String
        positions =
        MoreObjects.firstNonNull(candidateFilterRequest.getCandiadtePositions(), StringUtils.EMPTY);

    final Page<CandidateEntity>
        candidateEntitiesPage =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, positions, pageRequest);

    final List<CandidateDTO>
        candidateDTOs =
        converterService.convert(candidateEntitiesPage.getContent(), CandidateDTO.class);

    final PagingResponse<CandidateDTO>
        result =
        new PagingResponse<>(candidateEntitiesPage.getTotalElements(), candidateDTOs);

    return result;
  }

  @Transactional
  @Override
  public void deletePositionsByCandidate(CandidateDTO candidateDTO) {
    candidateDTO.setPositions(null);
    applicationsService.deleteApplicationsByCandidateDTO(candidateDTO);
  }

  @Transactional
  @Override
  public Long saveOrUpdate(CandidateDTO candidate) {
    Assert.notNull(candidate);
    CandidateEntity entity = converterService.convert(candidate, CandidateEntity.class);
    try {
      return candidateRepository.saveAndFlush(entity).getId();
    } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
      log.error("Save to repository failed.", ex);

      String candidateName = candidate.getName();

      throw new DuplicateCandidateException(candidateName, ex);
    }
  }

  @Transactional(readOnly = true)
  @Override
  public CandidateDTO getCandidateDtoByEmail(String email) {
    Assert.notNull(email);
    CandidateEntity candidateEntity = this.candidateRepository.findByEmail(email);
    if (candidateEntity != null) {
      return converterService.convert(candidateEntity, CandidateDTO.class);
    }
    return null;
  }

  @Transactional(readOnly = true)
  @Override
  public CandidateDTO getCandidateDtoByPhone(String phone) {
    Assert.notNull(phone);
    CandidateEntity candidateEntity = this.candidateRepository.findByPhone(phone);
    if (candidateEntity != null) {
      return converterService.convert(candidateEntity, CandidateDTO.class);
    }
    return null;
  }

  private void validateCandidateFilterRequest(CandidateFilterRequest candidateFilterRequest) {
    Assert.notNull(candidateFilterRequest);
    Assert.notNull(candidateFilterRequest.getPageSize());
    Assert.notNull(candidateFilterRequest.getPageNumber());

    if (candidateFilterRequest.getSortName() != null
        || candidateFilterRequest.getSortOrder() != null) {
      Assert.notNull(candidateFilterRequest.getSortName());
      Assert.notNull(candidateFilterRequest.getSortOrder());

      try {
        Sort.Direction.fromString(candidateFilterRequest.getSortOrder());
      } catch (Exception e) {
        throw new IllegalArgumentException(
            "Invalid sort order: " + candidateFilterRequest.getSortOrder(), e);
      }

      try {
        new PageRequest(0, 10, Sort.Direction.fromString(candidateFilterRequest.getSortOrder()),
            candidateFilterRequest.getSortName());
      } catch (Exception e) {
        throw new IllegalArgumentException(
            "Invalid sort name: " + candidateFilterRequest.getSortName(), e);
      }
    }
  }
}