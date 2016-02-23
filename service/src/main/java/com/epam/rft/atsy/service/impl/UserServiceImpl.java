package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.UserDAO;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
import com.epam.rft.atsy.service.UserService;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserServiceImpl.class);

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
            LOGGER.error("User failed to login",e);
            throw new BackendException(e);
        }

        return userDTO;

    }

}
