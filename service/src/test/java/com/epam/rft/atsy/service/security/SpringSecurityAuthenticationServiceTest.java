package com.epam.rft.atsy.service.security;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import com.epam.rft.atsy.service.exception.UserNotLoggedInException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class SpringSecurityAuthenticationServiceTest {

  private static final Long USER_ID = 1L;
  private static final String USER_NAME = "Test";
  private static final String USER_PASSWORD = "pass";

  @InjectMocks
  private SpringSecurityAuthenticationService springSecurityAuthenticationService;

  @Test
  public void getCurrentUserDetailsShouldReturnUserDetailsWhenUserIsLoggedIn()
      throws UserNotLoggedInException {
    //Given
    UserDetailsAdapter
        userDetailsAdapter =
        new UserDetailsAdapter(USER_ID, USER_PASSWORD, USER_NAME);

    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsAdapter, null));

    //When
    UserDetailsAdapter
        result =
        springSecurityAuthenticationService.getCurrentUserDetails();

    //Then
    assertThat(result, is(userDetailsAdapter));
  }

  @Test(expected = UserNotLoggedInException.class)
  public void getCurrentUserDetailsShouldNotReturnUserDetailsWhenUserIsNotLoggedIn()
      throws UserNotLoggedInException {
    //Given
    SecurityContextHolder.getContext().setAuthentication(null);

    //When
    UserDetailsAdapter result = springSecurityAuthenticationService.getCurrentUserDetails();

  }

  @Test
  public void isCurrentUserAuthenticatedShouldBeAuthenticatedWhenUserIsLoggedInAndAuthenticated() {
    //Given
    UserDetailsAdapter
        userDetailsAdapter =
        new UserDetailsAdapter(USER_ID, USER_PASSWORD, USER_NAME);

    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsAdapter, null,
            Arrays.asList(new SimpleGrantedAuthority("ROLE_GRANTED"))));

    //When
    boolean result = springSecurityAuthenticationService.isCurrentUserAuthenticated();

    //Then
    assertTrue(result);
  }

  @Test
  public void isCurrentUserAuthenticatedShouldNotBeAuthenticatedWhenUserIsLoggedAndNotAuthenticated() {
    //Given
    UserDetailsAdapter
        userDetailsAdapter =
        new UserDetailsAdapter(USER_ID, USER_PASSWORD, USER_NAME);

    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(userDetailsAdapter, null));

    //When
    boolean result = springSecurityAuthenticationService.isCurrentUserAuthenticated();

    //Then
    assertFalse(result);
  }

  @Test
  public void isCurrentUserAuthenticatedShouldNotBeAuthenticatedWhenUserIsNotLoggedIn() {
    //Given
    SecurityContextHolder.getContext()
        .setAuthentication(null);

    //When
    boolean result = springSecurityAuthenticationService.isCurrentUserAuthenticated();

    //Then
    assertFalse(result);
  }

}
