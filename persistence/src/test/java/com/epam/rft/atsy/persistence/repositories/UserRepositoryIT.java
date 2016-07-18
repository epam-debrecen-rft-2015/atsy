package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.UserEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class UserRepositoryIT extends AbstractRepositoryIT {

  private final String UNREGISTERED_USER_NAME = "unregistered";
  private final String UNREGISTERED_USER_PASSWORD = "not_a_pass";
  private final String REGISTERED_USER_NAME = "Test";
  private final String
      REGISTERED_USER_PASSWORD =
      "$2a$04$6r2eBlucnE3/m0fDYgE3e.eI5akUx55qPMID3O/SlptCIqOFFcCPK";

  @Autowired
  private UserRepository repository;

  @Test
  public void findByUserNameAndUserPasswordShouldFindUserForMatchingCredentials() {
    // When
    UserEntity
        test =
        repository.findByUserNameAndUserPassword(REGISTERED_USER_NAME, REGISTERED_USER_PASSWORD);

    // Then
    assertThat(test, notNullValue());
    assertThat(test.getUserName(), is(REGISTERED_USER_NAME));
    assertThat(test.getUserPassword(), is(REGISTERED_USER_PASSWORD));
    assertThat(test.getId(), is(1L));
  }

  @Test
  public void findByUserNameAndUserPasswordShouldNotFindUserForRegisteredUserNameButWrongPassword() {
    // When
    UserEntity
        test =
        repository.findByUserNameAndUserPassword(REGISTERED_USER_NAME, UNREGISTERED_USER_PASSWORD);

    // Then
    assertThat(test, nullValue());
  }

  @Test
  public void findByUserNameAndUserPasswordShouldNotFindUserForUnregisteredUserNameButKnownPassword() {
    // When
    UserEntity unregisteredUser = repository.findByUserNameAndUserPassword(UNREGISTERED_USER_NAME,
        REGISTERED_USER_PASSWORD);

    // Then
    assertThat(unregisteredUser, nullValue());
  }

  @Test
  public void findByUserNameAndUserPasswordShouldNotFindUserForWrongUserNameAndPassword() {
    // When
    UserEntity unregisteredUser = repository.findByUserNameAndUserPassword(UNREGISTERED_USER_NAME,
        UNREGISTERED_USER_PASSWORD);

    // Then
    assertThat(unregisteredUser, nullValue());
  }

  @Test
  public void findByUserNameShouldNotFindUserForUnregisteredUserName() {
    // When
    UserEntity unregisteredUser = repository.findByUserName(UNREGISTERED_USER_NAME);

    // Then
    assertThat(unregisteredUser, nullValue());
  }

  @Test
  public void findByUserNameShouldFindUserForRegisteredUserName() {
    // When
    UserEntity registeredUser = repository.findByUserName(REGISTERED_USER_NAME);

    // Then
    assertThat(registeredUser, notNullValue());
    assertThat(registeredUser.getUserName(), is(REGISTERED_USER_NAME));
    assertThat(registeredUser.getId(), is(1L));
    assertThat(registeredUser.getUserPassword(), is(REGISTERED_USER_PASSWORD));
  }
}
