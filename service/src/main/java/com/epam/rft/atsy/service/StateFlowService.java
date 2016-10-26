package com.epam.rft.atsy.service;


import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;

import java.util.Collection;
import java.util.List;

/**
 * Service that operates with state flows in the database layer and in the view layer.
 */
public interface StateFlowService {

  /**
   * returns the available states from the given state
   * @param statesDTO the state
   * @return the collection of available states.
   */
  Collection<StateFlowDTO> getStateFlowDTOByFromStateDTO(StateDTO statesDTO);
  boolean isAvailableFromLastState(List<StateHistoryDTO> representationList, String state);
}
