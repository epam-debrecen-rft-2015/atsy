package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.ExistingUserException;

/**
 * Created by tothd on 2015. 10. 21..
 */
public interface UserService {

    public UserDTO login(UserDTO user) throws ExistingUserException;
}
