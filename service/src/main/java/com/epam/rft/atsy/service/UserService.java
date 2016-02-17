package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.UserDTO;
import com.epam.rft.atsy.service.exception.UserNotFoundException;

public interface UserService {

    UserDTO login(UserDTO user) throws UserNotFoundException;
}
