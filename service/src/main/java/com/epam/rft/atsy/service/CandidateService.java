package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.persistence.request.CandidateRequestDTO;

import java.util.Collection;

/**
 * Created by tothd on 2015. 11. 07..
 */
public interface CandidateService {

    Collection<CandidateDTO> getAllCandidate(CandidateRequestDTO candidateRequestDTO);

}
