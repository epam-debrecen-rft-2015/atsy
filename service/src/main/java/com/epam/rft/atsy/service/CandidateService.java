package com.epam.rft.atsy.service;

import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.service.domain.CandidateDTO;

import java.util.Collection;

public interface CandidateService {

    CandidateDTO getCandidate(Long candidateID);

    Collection<CandidateDTO> getAllCandidate(FilterRequest sortingRequest);

    Long saveOrUpdate(CandidateDTO candidate);

}
