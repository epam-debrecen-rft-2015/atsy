package com.epam.rft.atsy.service.security;

import com.epam.rft.atsy.service.AuthenticationService;
import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * An AuthenticationService implementation, which is using
 * {@link SecurityContextHolder} from Spring Security.
 * Used for isolating our dependency on Spring Security.
 */
@Service
public class SpringSecurityAuthenticationService implements AuthenticationService {

  @Override
  public UserDetailsAdapter getCurrentUserDetails() throws UserNotLoggedInException {

    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      UserDetailsAdapter
          userDetailsAdapter =
          (UserDetailsAdapter) SecurityContextHolder.getContext().getAuthentication()
              .getPrincipal();
      if (userDetailsAdapter != null) {
        return userDetailsAdapter;
      }
    }

    throw new UserNotLoggedInException();
  }

  @Override
  public boolean isCurrentUserAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
  }

}
