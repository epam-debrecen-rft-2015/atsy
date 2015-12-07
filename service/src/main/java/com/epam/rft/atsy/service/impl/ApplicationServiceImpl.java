package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.ApplicationDAO;
import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by tothd on 2015. 12. 07..
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    ApplicationDAO applicationDAO;

    @Resource
    ModelMapper modelMapper;

    @Override
    public Collection<StateDTO> getStates(Long id) {
        return null;
    }

    @Override
    public void saveState(StateDTO state) {

    }
}
