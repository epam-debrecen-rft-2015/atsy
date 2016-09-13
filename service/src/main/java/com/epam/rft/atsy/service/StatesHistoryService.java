package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.service.response.PagingResponse;

import java.util.List;

/**
 * Service that operates with states in the database layer and in the view layer.
 */
public interface StatesHistoryService {

  /**
   * Returns a PagingResponse object for a candidate, which contains a list of
   * CandidateApplicationDTOs with the number of size, and the number of all
   * CandidateApplicationDTOs.
   * @param id id the candidate's id
   * @param page number of the requested page
   * @param size the requested page size
   * @return a PagingResponse object
   */
  PagingResponse<CandidateApplicationDTO> getCandidateApplicationsByCandidateIdOrderByModificationDateDesc(
      Long id, int page, int size);

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
