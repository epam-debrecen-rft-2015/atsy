package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Resource
    private ModelMapper modelMapper;

    public UserDTO login(UserDTO user) throws UserNotFoundException {

        UserDTO userDTO;
        try {
            UserEntity userEntity = userRepository.findByUserNameAndUserPassword(user.getName(), user.getPassword());
            userDTO=modelMapper.map(userEntity,UserDTO.class);
        } catch (NoResultException | EmptyResultDataAccessException | NonUniqueResultException e) {
            throw new UserNotFoundException(e);
        }catch (Exception e){
            log.error("User failed to login",e);
            throw new BackendException(e);
        }

        return userDTO;

    }

    @Override
    public Long saveOrUpdate(UserDTO userDTO) {
        Assert.notNull(userDTO);
        UserEntity entity = modelMapper.map(userDTO, UserEntity.class);
        try {
            return userRepository.save(entity).getId();
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            log.error("Save to repository failed.", ex);

            String userName = entity.getUserName();

            throw new DuplicateRecordException(userName,
                                               "Duplication occurred when saving user: " + userName, ex);
        }
    }

    @Override
    public UserDTO findUserByName(String username) {
        UserEntity user = userRepository.findByUserName(username);
        return (user != null) ? modelMapper.map(user, UserDTO.class) : null;
    }

}
