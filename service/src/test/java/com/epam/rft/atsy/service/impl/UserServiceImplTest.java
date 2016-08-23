package com.epam.rft.atsy.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  private static final long USER_ID = 1L;
  private static final String USER_NAME = "Test";
  private static final String USER_PASSWORD = "password";

  private static final String NOT_REAL_USER_NAME = "notreal";
  private static final String NULL_USER_NAME = null;

  private static final UserDTO NULL_USER_DTO = null;
  private static final UserEntity NULL_USER_ENTITY = null;

  private UserDTO userDTO;
  private UserDTO detailedUserDTO;
  private UserEntity userEntity;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ConverterService converterService;

  @InjectMocks
  private UserServiceImpl userService;

  @Before
  public void setUp() {
    userDTO = UserDTO.builder().name(USER_NAME).password(USER_PASSWORD).build();
    detailedUserDTO = UserDTO.builder().id(USER_ID).name(USER_NAME).password(USER_PASSWORD).build();
    userEntity =
        UserEntity.builder().id(USER_ID).userName(USER_NAME).userPassword(USER_PASSWORD).build();

    given(converterService.convert(userEntity, UserDTO.class)).willReturn(detailedUserDTO);
    given(converterService.convert(detailedUserDTO, UserEntity.class)).willReturn(userEntity);
  }

  @Test(expected = UserNotFoundException.class)
  public void loginShouldThrowUserNotFoundExceptionBecauseOfNoResultException()
      throws UserNotFoundException {
    //Given
    given(userRepository.findByUserNameAndUserPassword(USER_NAME, USER_PASSWORD))
        .willThrow(NoResultException.class);

    //When
    userService.login(userDTO);
  }

  @Test(expected = UserNotFoundException.class)
  public void loginShouldThrowUserNotFoundExceptionBecauseOfEmptyResultDataAccessException()
      throws UserNotFoundException {
    //Given
    given(userRepository.findByUserNameAndUserPassword(USER_NAME, USER_PASSWORD))
        .willThrow(EmptyResultDataAccessException.class);

    //When
    userService.login(userDTO);
  }

  @Test(expected = UserNotFoundException.class)
  public void loginShouldThrowUserNotFoundExceptionBecauseOfNonUniqueResultException()
      throws UserNotFoundException {
    //Given
    given(userRepository.findByUserNameAndUserPassword(USER_NAME, USER_PASSWORD))
        .willThrow(NonUniqueResultException.class);

    //When
    userService.login(userDTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loginShouldThrowIllegalArgumentExceptionBecauseOfNullParameter()
      throws UserNotFoundException {
    //When
    userService.login(NULL_USER_DTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loginShouldThrowIllegalArgumentExceptionBecauseUserDtoHasNullName()
      throws UserNotFoundException {
    //Given
    UserDTO userDtoWithNullName = UserDTO.builder().name(null).password(USER_PASSWORD).build();

    //When
    userService.login(userDtoWithNullName);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loginShouldThrowIllegalArgumentExceptionBecauseUserDtoHasNullPassword()
      throws UserNotFoundException {
    //Given
    UserDTO userDtoWithNullPassword = UserDTO.builder().name(USER_NAME).password(null).build();

    //When
    userService.login(userDtoWithNullPassword);
  }

  @Test
  public void loginShouldReturnDetailedUserDto() throws UserNotFoundException {
    //Given
    given(userRepository.findByUserNameAndUserPassword(USER_NAME, USER_PASSWORD))
        .willReturn(userEntity);

    //When
    UserDTO result = userService.login(userDTO);

    //Then
    then(converterService).should().convert(userEntity, UserDTO.class);
    then(userRepository).should().findByUserNameAndUserPassword(USER_NAME, USER_PASSWORD);
    assertThat(result, is(detailedUserDTO));
  }

  @Test(expected = UserNotFoundException.class)
  public void loginShouldNotReturnDetailedUserDto() throws UserNotFoundException {
    //Given
    given(userRepository.findByUserNameAndUserPassword(USER_NAME, USER_PASSWORD))
        .willReturn(NULL_USER_ENTITY);

    //When
    UserDTO result = userService.login(userDTO);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateShouldThrowIllegalArgumentExceptionBecauseNullParameter() {
    //When
    userService.saveOrUpdate(NULL_USER_DTO);
  }

  @Test(expected = DuplicateRecordException.class)
  public void saveOrUpdateShouldThrowDuplicateRecordExceptionBecauseOfConstraintViolationException() {
    //Given
    given(userRepository.saveAndFlush(userEntity)).willThrow(ConstraintViolationException.class);

    //When
    userService.saveOrUpdate(detailedUserDTO);
  }

  @Test(expected = DuplicateRecordException.class)
  public void saveOrUpdateShouldThrowDuplicateRecordExceptionBecauseOfDataIntegrityViolationException() {
    //Given
    given(userRepository.saveAndFlush(userEntity)).willThrow(DataIntegrityViolationException.class);

    //When
    userService.saveOrUpdate(detailedUserDTO);
  }

  @Test
  public void saveOrUpdateShouldReturnUserId() {
    //Given
    given(userRepository.saveAndFlush(userEntity)).willReturn(userEntity);

    //When
    Long result = userService.saveOrUpdate(detailedUserDTO);

    //Then
    then(converterService).should().convert(detailedUserDTO, UserEntity.class);
    then(userRepository).should().saveAndFlush(userEntity);
    assertThat(result, is(USER_ID));
  }

  @Test
  public void findUserByNameShouldReturnNullWhenNoUserWasFound() {
    //Given
    given(userRepository.findByUserName(NOT_REAL_USER_NAME)).willReturn(NULL_USER_ENTITY);

    //When
    UserDTO result = userService.findUserByName(NOT_REAL_USER_NAME);

    // Then
    then(userRepository).should().findByUserName(NOT_REAL_USER_NAME);
    assertNull(result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void findUserByNameShouldThrowIllegalArgumentExceptionWhenParameterIsNull() {
    //When
    userService.findUserByName(NULL_USER_NAME);
  }

  @Test
  public void findUserByNameShouldReturnUserDtoWhenUserWasFound() {
    //Given
    given(userRepository.findByUserName(USER_NAME)).willReturn(userEntity);

    //When
    UserDTO result = userService.findUserByName(USER_NAME);

    //Then
    then(converterService).should().convert(userEntity, UserDTO.class);
    then(userRepository).should().findByUserName(USER_NAME);
    assertThat(result, is(detailedUserDTO));
  }
}
