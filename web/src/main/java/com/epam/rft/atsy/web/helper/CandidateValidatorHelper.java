package com.epam.rft.atsy.web.helper;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * This class provides validation methods for candidates.
 */
@Component
public class CandidateValidatorHelper {

  @Autowired
  private CandidateService candidateService;

  /**
   * Validates non-existing candidateDTO which should be unique(id, email, phone).
   *
   * @param candidateDTO the candidateDTO which is waiting for validation
   * @return true if the candidate is unique, otherwise false
   */
  public boolean isValidNonExistingCandidate(CandidateDTO candidateDTO) {
    this.preValidateNonExistingCandidate(candidateDTO);

    CandidateDTO
        candidateDTOByEmail =
        this.candidateService.getCandidateDtoByEmail(candidateDTO.getEmail());
    CandidateDTO
        candidateDTOByPhone =
        this.candidateService.getCandidateDtoByPhone(candidateDTO.getPhone());

    if (candidateDTOByEmail == null && candidateDTOByPhone == null) {
      return true;
    }
    return false;
  }

  /**
   * Validates existing candidateDTO which should be unique(id, email, phone).
   *
   * @param candidateDTO the candidateDTO which is waiting for validation
   * @return true if the candidate is unique, otherwise false
   */
  public boolean isValidExistingCandidate(CandidateDTO candidateDTO) {
    this.preValidateExistingCandidate(candidateDTO);
    Long candidateId = candidateDTO.getId();

    CandidateDTO
        candidateDTOByEmail =
        this.candidateService.getCandidateDtoByEmail(candidateDTO.getEmail());
    CandidateDTO
        candidateDTOByPhone =
        this.candidateService.getCandidateDtoByPhone(candidateDTO.getPhone());

    if ((candidateDTOByEmail == null || candidateDTOByEmail.getId().equals(candidateId)) &&
        (candidateDTOByPhone == null || candidateDTOByPhone.getId().equals(candidateId))) {
      return true;
    }
    return false;
  }

  private void preValidateNonExistingCandidate(CandidateDTO candidateDTO) {
    Assert.notNull(candidateDTO);
    Assert.notNull(candidateDTO.getEmail());
    Assert.notNull(candidateDTO.getPhone());
  }

  private void preValidateExistingCandidate(CandidateDTO candidateDTO) {
    this.preValidateNonExistingCandidate(candidateDTO);
    Assert.notNull(candidateDTO.getId());
  }
}
