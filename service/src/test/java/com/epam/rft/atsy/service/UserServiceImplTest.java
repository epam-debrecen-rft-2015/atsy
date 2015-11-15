package com.epam.rft.atsy.service;

import com.epam.rft.atsy.persistence.dao.UserDAO;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.BackendException;
import com.epam.rft.atsy.service.exception.UserNotFoundException;
import com.epam.rft.atsy.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

/**
 * Created by tothd on 2015. 10. 26..
 */
public class UserServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTest.class);


    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserDAO userDAO;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserEntity userEntity;

    @Mock
    UserDTO userDTO;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void loginTest() throws UserNotFoundException {
        given(userEntity.getUserName()).willReturn("test");
        given(userEntity.getUserPassword()).willReturn("pass1");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(modelMapper.map(userEntity, UserDTO.class)).willReturn(userDTO);
        given(userDAO.login("test", "pass1")).willReturn(userEntity);
        //when
        UserDTO resultUserDTO = userService.login(userDTO);
        //then
        assertThat(resultUserDTO.getName(), is(equalToIgnoringCase("test")));
        assertThat(resultUserDTO.getPassword(), is(equalToIgnoringCase("pass1")));


    }

    @Test(expectedExceptions = BackendException.class)
    public void loginWithBackendException() throws UserNotFoundException {
        given(userEntity.getUserName()).willReturn("test");
        given(userEntity.getUserPassword()).willReturn("pass1");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");

        given(modelMapper.map(userEntity, UserDTO.class)).willReturn(userDTO);

        given(userDAO.login("test", "pass1")).willReturn(userEntity);

        UserDTO resultUserDTO = userService.login(null);

    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void loginWithNoResultException() throws UserNotFoundException {
        given(userEntity.getUserName()).willReturn("test");
        given(userEntity.getUserPassword()).willReturn("pass1");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");
        given(userDAO.login("test", "pass1")).willThrow(NoResultException.class);
        given(modelMapper.map(userEntity, UserDTO.class)).willReturn(userDTO);

        UserDTO resultUserDTO = userService.login(userDTO);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void loginWithNoUniqueResultException() throws UserNotFoundException {

        given(userEntity.getUserName()).willReturn("test");
        given(userEntity.getUserPassword()).willReturn("pass1");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");

        given(userDAO.login("test", "pass1")).willThrow(NonUniqueResultException.class);

        given(modelMapper.map(userEntity, UserDTO.class)).willReturn(userDTO);

        UserDTO resultUserDTO = userService.login(userDTO);
    }

    @Test(expectedExceptions = UserNotFoundException.class)
    public void loginWithEmptyResultDataAccessException() throws UserNotFoundException {

        given(userEntity.getUserName()).willReturn("test");
        given(userEntity.getUserPassword()).willReturn("pass1");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");

        given(userDAO.login("test", "pass1")).willThrow(EmptyResultDataAccessException.class);

        given(modelMapper.map(userEntity, UserDTO.class)).willReturn(userDTO);

        UserDTO resultUserDTO = userService.login(userDTO);
    }
}
