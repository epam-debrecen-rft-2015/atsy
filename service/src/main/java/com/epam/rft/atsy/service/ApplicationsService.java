package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;

import java.util.List;

/**
 * Service that operates with applications in the database layer and in the view layer.
 */
public interface ApplicationsService {

  /**
   * Returns a list of the applications of a specified candidate.
   * @param candidateDTO the candidate
   * @return a list of applications
   */
  List<ApplicationDTO> getApplicationsByCandidateDTO(CandidateDTO candidateDTO);

  //TODO: Add java doc. Add test.
  void deleteApplicationsByCandidateDTO(CandidateDTO candidateDTO);

  /**
   * Saves an application to the database and returns it's id.
   * @param applicationDTO the application
   * @return the id of application
   */
  Long saveOrUpdate(ApplicationDTO applicationDTO);

  /**
   * Saves an application and a state to the database and returns the application's id.
   * @param applicationDTO the application
   * @param stateHistoryDTO the state of application
   * @return the id of application
   */
  Long saveApplication(ApplicationDTO applicationDTO, StateHistoryDTO stateHistoryDTO);
}
