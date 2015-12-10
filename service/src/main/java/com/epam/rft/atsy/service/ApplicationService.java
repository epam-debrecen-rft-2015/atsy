package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;

import java.util.Collection;

/**
 * Created by tothd on 2015. 12. 07..
 */
public interface ApplicationService {

    Collection<CandidateApplicationDTO> getStatesByCandidateId(Long id);

    Long saveState(StateDTO state);

}
