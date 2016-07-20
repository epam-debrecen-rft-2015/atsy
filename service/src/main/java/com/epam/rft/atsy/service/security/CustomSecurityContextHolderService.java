package com.epam.rft.atsy.service.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Wrapper service for {@link SecurityContextHolder}.
 * Used for isolating our dependency on Spring Security.
 */
@Service
public class CustomSecurityContextHolderService {

  /**
   * Getter for the current user's {@link UserDetailsAdapter}.
   * @return the current user's {@code UserDetailsAdapter}
   */
  public UserDetailsAdapter getCurrentUserDetailsAdapter() {
    return (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
  }

  /**
   * Checks if the current user is authenticated.
   * @return true if the user is authenticated, false otherwise
   */
  public boolean isCurrentUserAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
  }

}
