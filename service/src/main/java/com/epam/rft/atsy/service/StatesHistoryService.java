package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;

import java.util.Collection;
import java.util.List;

/**
 * Service that operates with states in the database layer and in the view layer.
 */
public interface StatesHistoryService {

  /**
   * returns the collection of applications of the candidate.
   * @param id the candidate's id
   * @return the collection of applications
   */
  Collection<CandidateApplicationDTO> getCandidateApplicationsByCandidateIdOrderByModificationDateDesc(
      Long id);

  /**
   * Saves a state of the given application and returns it's id.
   * @param state the state
   * @param applicationId the application's id
   * @return the state's id
   */
  Long saveStateHistory(StateHistoryDTO state, Long applicationId);

  /**
   * Returns the list of states of the given application.
   * @param applicationId the application's id
   * @return the list of states of the application
   */
  List<StateHistoryDTO> getStateHistoriesByApplicationId(Long applicationId);

}
