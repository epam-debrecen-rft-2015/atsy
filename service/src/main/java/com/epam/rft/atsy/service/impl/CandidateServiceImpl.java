package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private ConverterService converterService;

  @Transactional(readOnly = true)
  @Override
  public CandidateDTO getCandidate(Long id) {
    Assert.notNull(id);

    CandidateEntity candidateEntity = candidateRepository.findOne(id);
    return converterService.convert(candidateEntity, CandidateDTO.class);
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
  public Collection<CandidateDTO> getAllCandidate(FilterRequest sortingRequest) {
    validateFilterRequest(sortingRequest);

    SearchOptions searchOptions = sortingRequest.getSearchOptions();

    Sort.Direction sortDirection = Sort.Direction.fromString(sortingRequest.getOrder().name());

    Sort sort = new Sort(sortDirection, sortingRequest.getFieldName().toString());

    List<CandidateEntity>
        candidateEntities =
        candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(
            searchOptions.getName(), searchOptions.getEmail(), searchOptions.getPhone(), sort);

    return converterService.convert(candidateEntities, CandidateDTO.class);
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

  /**
   * Validates the fields of the passed filter request. Performs nullness-checks.
   * @param filterRequest the object to validate
   * @throws IllegalArgumentException if any member of the parameter (or the parameter itself) is
   * {@code null}.
   */
  private void validateFilterRequest(FilterRequest filterRequest) {
    Assert.notNull(filterRequest);
    Assert.notNull(filterRequest.getFieldName());
    Assert.notNull(filterRequest.getOrder());

    SearchOptions searchOptions = filterRequest.getSearchOptions();

    Assert.notNull(searchOptions);
  }
}
