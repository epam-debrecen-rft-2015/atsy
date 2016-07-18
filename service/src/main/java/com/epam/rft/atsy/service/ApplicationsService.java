package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;

/**
 * Service that operates with applications in the database layer and in the view layer.
 */
public interface ApplicationsService {

  /**
   * Saves an application to the database and returns it's id.
   *
   * @param applicationDTO the application
   * @return the id of application
   */
  Long saveOrUpdate(ApplicationDTO applicationDTO);

  /**
   * Saves an application and a state to the database and returns the application's id.
   *
   * @param applicationDTO the application
   * @param stateDTO       the state of application
   * @return the id of application
   */
  Long saveApplicaton(ApplicationDTO applicationDTO, StateDTO stateDTO);
}
