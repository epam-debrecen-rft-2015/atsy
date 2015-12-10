package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.ApplicationDAO;
import com.epam.rft.atsy.persistence.entities.states.NewStateEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tothd on 2015. 12. 07..
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationDAO applicationDAO;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public Collection<CandidateApplicationDTO> getStatesByCandidateId(Long id) {
        Collection<StateEntity> stateEntities = applicationDAO.loadByCandidateId(id);
        Type targetListType = new TypeToken<List<StateDTO>>() {
        }.getType();

        List<CandidateApplicationDTO> candidateApplicationDTOList=new LinkedList<>();
        List<StateDTO> stateDTOs=modelMapper.map(stateEntities, targetListType);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (StateDTO stateDTO : stateDTOs){
            CandidateApplicationDTO candidateApplicationDTO=new CandidateApplicationDTO();
            candidateApplicationDTO.setCreationDate(simpleDateFormat.format(stateDTO.getCreationDate()));
            while(stateDTO.getNextState()!=null){
                stateDTO=stateDTO.getNextState();
            }

            candidateApplicationDTO.setStateType(stateDTO.getStateType());
            candidateApplicationDTO.setPositionName(stateDTO.getPosition().getName());
            candidateApplicationDTO.setLastStateId(stateDTO.getStateId());
            candidateApplicationDTO.setModificationDate(simpleDateFormat.format(stateDTO.getCreationDate()));

            candidateApplicationDTOList.add(candidateApplicationDTO);
        }

        return candidateApplicationDTOList;
    }

    @Override
    public Long saveState(StateDTO state) {
        NewStateEntity stateEntity = modelMapper.map(state, NewStateEntity.class);

        Long stateId=applicationDAO.create(stateEntity).getStateId();

        return stateId;
    }
}
