package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService {

  private final static Type CANDIDATEDTO_LIST_TYPE = new TypeToken<List<CandidateDTO>>() {
  }.getType();
  @Resource
  private ModelMapper modelMapper;
  @Autowired
  private CandidateRepository candidateRepository;

  @Transactional(readOnly = true)
  @Override
  public CandidateDTO getCandidate(Long id) {
    Assert.notNull(id);

    CandidateEntity candidateEntity = candidateRepository.findOne(id);
    return modelMapper.map(candidateEntity, CandidateDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public Collection<CandidateDTO> getAllCandidate(FilterRequest sortingRequest) {
    validateFilterRequest(sortingRequest);

    SearchOptions searchOptions = sortingRequest.getSearchOptions();

    Sort.Direction sortDirection = Sort.Direction.fromString(sortingRequest.getOrder().name());

    Sort sort = new Sort(sortDirection, sortingRequest.getFieldName());

    Collection<CandidateEntity>
        candidateEntities =
        candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(
            searchOptions.getName(), searchOptions.getEmail(), searchOptions.getPhone(), sort);

    return modelMapper.map(candidateEntities, CANDIDATEDTO_LIST_TYPE);
  }

  @Transactional
  @Override
  public Long saveOrUpdate(CandidateDTO candidate) {
    Assert.notNull(candidate);
    CandidateEntity entity = modelMapper.map(candidate, CandidateEntity.class);
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

  @Transactional(readOnly = true)
  @Override
  public CandidateDTO getCandidateByName(String name) {
    CandidateEntity candidateEntity = candidateRepository.findByName(name);
    return modelMapper.map(candidateEntity, CandidateDTO.class);
  }

}
