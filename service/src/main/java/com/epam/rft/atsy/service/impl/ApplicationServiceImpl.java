package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.ApplicationDAO;
import com.epam.rft.atsy.persistence.entities.states.NewStateEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

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
    public Collection<StateDTO> getStatesByCandidateId(Long id) {
        Collection<StateEntity> stateEntities = applicationDAO.loadByCandidateId(id);
        Type targetListType = new TypeToken<List<StateDTO>>() {
        }.getType();

        return modelMapper.map(stateEntities, targetListType);
    }

    @Override
    public Long saveState(StateDTO state) {
        NewStateEntity stateEntity = modelMapper.map(state, NewStateEntity.class);

        Long stateId=applicationDAO.create(stateEntity).getStateId();

        return stateId;
    }
}
