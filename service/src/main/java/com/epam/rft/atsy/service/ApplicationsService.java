package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.service.response.PagingResponse;

import java.util.List;

/**
 * Service that operates with applications in the database layer and in the view layer.
 */
public interface ApplicationsService {

  /**
   * Returns the application with the specified id.
   * @param applicationId the id of the searched application
   * @return the application
   */
  ApplicationDTO getApplicationDtoById(Long applicationId);

  /**
   * Returns a list of the applications of a specified candidate.
   * @param candidateDTO the candidate
   * @return a list of applications
   */
  List<ApplicationDTO> getApplicationsByCandidateDTO(CandidateDTO candidateDTO);

  /**
   * Returns a PagingResponse object for a candidate, which contains a list of ApplicationDTOs with
   * the number of size, and the number of all ApplicationDTOs.
   * @param candidateId the id of the candidate
   * @param page the number of the required page
   * @param size the required page size
   * @return a PagingResponse object
   */
  PagingResponse<CandidateApplicationDTO> getApplicationsByCandidateId(Long candidateId,
                                                                       int page,
                                                                       int size);

  void deleteApplicationsByCandidateDTO(CandidateDTO candidateDTO);

  /**
   * Saves an application to the database and returns it's id.
   * @param applicationDTO the application
   * @return the id of application
   */
  ApplicationDTO saveOrUpdate(ApplicationDTO applicationDTO);

  /**
   * Saves an application and a state to the database and returns the application's id.
   * @param applicationDTO the application
   * @param stateHistoryDTO the state of application
   * @return the id of application
   */
  Long saveApplication(ApplicationDTO applicationDTO, StateHistoryDTO stateHistoryDTO);
}
