package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.persistence.request.CandidateRequestDTO;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by tothd on 2015. 11. 07..
 */
@Service
public class CandidateServiceImpl implements CandidateService{
    @Override
    public Collection<CandidateDTO> getAllCandidate(CandidateRequestDTO candidateRequestDTO) {
        return null;
    }
}
