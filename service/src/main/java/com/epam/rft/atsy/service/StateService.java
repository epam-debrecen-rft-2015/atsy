package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.states.StateDTO;

/**
 * Service responsible for accessing state types.
 */
public interface StateService {

  /**
   * Returns the requested state with the specified ID.
   * @param id the id of the state
   * @return the state with the specified ID
   */
  StateDTO getStateDtoById(Long id);

  /**
   * Returns the requested state with the specified name.
   * @param name the name of the state
   * @return the state with the specified name
   */
  StateDTO getStateDtoByName(String name);

}
