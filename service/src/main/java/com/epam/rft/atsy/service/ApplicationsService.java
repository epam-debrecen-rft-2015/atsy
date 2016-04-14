package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.ApplicationDTO;

public interface ApplicationsService {

    Long saveOrUpdate(ApplicationDTO applicationDTO);

}
