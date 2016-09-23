package com.epam.rft.atsy.web.helper;


import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CandidateValidatorHelper {
  private static final String CANDIDATE_ERROR_DUPLICATE_MESSAGE_KEY = "candidate.error.duplicate";

  @Autowired
  private MessageKeyResolver messageKeyResolver;
  @Autowired
  private CandidateService candidateService;

  public ResponseEntity validateNonExistingCandidate(CandidateDTO candidateDTO) {
    preValidateCandidate(candidateDTO);

    CandidateDTO candidateDTOByEmail = this.candidateService.getCandidateDtoByEmail(candidateDTO.getEmail());
    CandidateDTO candidateDTOByPhone = this.candidateService.getCandidateDtoByPhone(candidateDTO.getPhone());

    if (candidateDTOByEmail == null && candidateDTOByPhone == null) {
      return new ResponseEntity(RestResponse.NO_ERROR, HttpStatus.OK);
    }

    String errorMessage = messageKeyResolver.resolveMessageOrDefault(CANDIDATE_ERROR_DUPLICATE_MESSAGE_KEY);
    return new ResponseEntity(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity validateExistingCandidate(CandidateDTO candidateDTO) {
    preValidateCandidate(candidateDTO);
    Long candidateId = candidateDTO.getId();

    CandidateDTO candidateDTOByEmail = this.candidateService.getCandidateDtoByEmail(candidateDTO.getEmail());
    CandidateDTO candidateDTOByPhone = this.candidateService.getCandidateDtoByPhone(candidateDTO.getPhone());

    if ((candidateDTOByEmail == null || candidateDTOByEmail.getId().equals(candidateId)) &&
        (candidateDTOByPhone == null || candidateDTOByPhone.getId().equals(candidateId))) {
      return new ResponseEntity(RestResponse.NO_ERROR, HttpStatus.OK);
    }

    String errorMessage = messageKeyResolver.resolveMessageOrDefault(CANDIDATE_ERROR_DUPLICATE_MESSAGE_KEY);
    return new ResponseEntity(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
  }

  private void preValidateCandidate(CandidateDTO candidateDTO) {
    Assert.notNull(candidateDTO);
    Assert.notNull(candidateDTO.getEmail());
    Assert.notNull(candidateDTO.getPhone());
  }
}
