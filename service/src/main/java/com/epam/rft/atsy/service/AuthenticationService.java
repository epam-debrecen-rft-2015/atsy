package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import com.epam.rft.atsy.service.security.UserDetailsAdapter;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Service interface for authentication purposes.
 * Each implementation of this interface should handle
 * the current user's details.
 * @see UserDetailsAdapter
 */
public interface AuthenticationService {

  /**
   * Getter for the current user's {@link UserDetails}.
   * @return the current user's {@code UserDetails}
   */
  public UserDetails getCurrentUserDetails() throws UserNotLoggedInException;

  /**
   * Checks if the current user is authenticated.
   * @return true if the user is authenticated, false otherwise
   */
  public boolean isCurrentUserAuthenticated();

}
