package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;

public interface ApplicationsService {

    Long saveOrUpdate(ApplicationDTO applicationDTO);

}
