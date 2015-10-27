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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void loginTest(){


        given(userEntity.getUserName()).willReturn("test");
        given(userEntity.getUserPassword()).willReturn("pass1");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");

        given(modelMapper.map(userEntity, UserDTO.class)).willReturn(userDTO);

        given(userDAO.login("test", "pass1")).willReturn(userEntity);



        try {

            //when
            UserDTO resultUserDTO = userService.login(userDTO);

            //then
            assertThat(resultUserDTO.getName(), is(equalToIgnoringCase("test")));
            assertThat(resultUserDTO.getPassword(), is(equalToIgnoringCase("pass1")));

        } catch (UserNotFoundException e) {
            LOGGER.error("User not found",e);
        }

    }

    @Test(expectedExceptions = BackendException.class)
    public void loginWithBackendException()  {
        
        given(userEntity.getUserName()).willReturn("test");
        given(userEntity.getUserPassword()).willReturn("pass1");
        given(userDTO.getName()).willReturn("test");
        given(userDTO.getPassword()).willReturn("pass1");

        given(modelMapper.map(userEntity, UserDTO.class)).willReturn(userDTO);

        given(userDAO.login("test", "pass1")).willReturn(userEntity);

        try {
            UserDTO resultUserDTO = userService.login(null);
        } catch (UserNotFoundException e) {
            LOGGER.info("BackendException tested");
        }

    }


}
