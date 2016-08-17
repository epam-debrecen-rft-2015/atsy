package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ConverterService converterService;

  @Transactional(readOnly = true)
  public UserDTO login(UserDTO user) throws UserNotFoundException {
    Assert.notNull(user);
    Assert.notNull(user.getName());
    Assert.notNull(user.getPassword());

    UserEntity userEntity;
    try {
      userEntity =
          userRepository.findByUserNameAndUserPassword(user.getName(), user.getPassword());
      if (userEntity == null) {
        throw new UserNotFoundException();
      }

    } catch (NoResultException | EmptyResultDataAccessException | NonUniqueResultException e) {
      throw new UserNotFoundException(e);
    } catch (UserNotFoundException e) {
      throw e;
    } catch (Exception e) {
      log.error("User failed to login", e);
      throw new BackendException(e);
    }

    UserDTO userDTO = converterService.convert(userEntity, UserDTO.class);

    return userDTO;
  }

  @Transactional
  @Override
  public Long saveOrUpdate(UserDTO userDTO) {
    Assert.notNull(userDTO);
    UserEntity entity = converterService.convert(userDTO, UserEntity.class);
    try {
      return userRepository.saveAndFlush(entity).getId();
    } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
      log.error("Save to repository failed.", ex);

      String userName = entity.getUserName();

      throw new DuplicateRecordException(userName,
          "Duplication occurred when saving user: " + userName, ex);
    }
  }

  @Transactional(readOnly = true)
  @Override
  public UserDTO findUserByName(String username) {
    Assert.notNull(username);
    UserEntity user = userRepository.findByUserName(username);
    return (user != null) ? converterService.convert(user, UserDTO.class) : null;
  }

}
