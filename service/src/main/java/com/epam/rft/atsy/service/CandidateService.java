package com.epam.rft.atsy.service;

import com.epam.rft.atsy.persistence.request.SortingRequest;
import com.epam.rft.atsy.service.domain.CandidateDTO;

import java.util.Collection;

/**
 * Created by tothd on 2015. 11. 07..
 */
public interface CandidateService {

    CandidateDTO getCandidate(Long candidateID);

    Collection<CandidateDTO> getAllCandidate(SortingRequest sortingRequest);

    Long saveOrUpdate(CandidateDTO candidate);

}
