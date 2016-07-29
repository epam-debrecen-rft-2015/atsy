package com.epam.rft.atsy.service;


import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;

import java.util.Collection;

public interface StateFlowService {

  Collection<StateFlowDTO> getStateFlowDTOByFromStateDTO(StateDTO statesDTO);
}
