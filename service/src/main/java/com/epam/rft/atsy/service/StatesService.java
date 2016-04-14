package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateViewDTO;

import java.util.Collection;
import java.util.List;

public interface StatesService {

    Collection<CandidateApplicationDTO> getStatesByCandidateId(Long id);

    Long saveState(StateDTO state, Long applicationId);

    List<StateViewDTO> getStatesByApplicationId(Long latestStateId);


}
