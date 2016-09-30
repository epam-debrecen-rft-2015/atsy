package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;

import java.util.List;

/**
 * Service that operates with states in the database layer and in the view layer.
 */
public interface StatesHistoryService {

  void deleteStateHistoriesByApplication(ApplicationDTO applicationDTO);

  /**
   * Saves a state of the given application and returns it's id.
   * @param state the state
   * @return the state's id
   */
  Long saveStateHistory(StateHistoryDTO state);

  /**
   * Returns the list of states of the given application.
   * @param applicationId the application's id
   * @return the list of states of the application
   */
  List<StateHistoryDTO> getStateHistoriesByApplicationId(Long applicationId);

}
