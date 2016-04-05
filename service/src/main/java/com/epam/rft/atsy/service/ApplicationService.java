package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;

import java.util.Collection;
import java.util.List;

public interface ApplicationService {

    Collection<CandidateApplicationDTO> getStatesByCandidateId(Long id);

    Long saveState(StateDTO state);

    List<StateDTO> getStatesByStateId(Long latestStateId);

}
