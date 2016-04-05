package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationRepository;
import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ApplicationServiceImpl implements ApplicationService {


    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Collection<CandidateApplicationDTO> getStatesByCandidateId(Long id) {
        Collection<StateEntity> stateEntities = applicationRepository.findByCandidateId(id);
        Type targetListType = new TypeToken<List<StateDTO>>() {
        }.getType();

        List<CandidateApplicationDTO> candidateApplicationDTOList=new LinkedList<>();
        List<StateDTO> stateDTOs=modelMapper.map(stateEntities, targetListType);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        StateDTO lastStateDTO = null;
        for (StateDTO stateDTO : stateDTOs){
            if(stateDTO.equals(lastStateDTO))
                continue;
            CandidateApplicationDTO candidateApplicationDTO=new CandidateApplicationDTO();
            candidateApplicationDTO.setCreationDate(simpleDateFormat.format(stateDTO.getCreationDate()));
            while(stateDTO.getNextState()!=null){
                stateDTO=stateDTO.getNextState();
            }
            lastStateDTO = stateDTO;
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
        StateEntity stateEntity = modelMapper.map(state, StateEntity.class);

        stateEntity.setCreationDate(new Date());

        return applicationRepository.save(stateEntity).getStateId();
    }

    @Override
    public List<StateDTO> getStatesByStateId(Long latestStateId) {
        List<StateDTO> stateDTOs = new ArrayList<>();
        for (StateEntity stateEntity: applicationRepository.findAll()) {
            stateDTOs.add(modelMapper.map(stateEntity,StateDTO.class));
        }
        List<StateDTO> newstates = new ArrayList<>();
        for(StateDTO stateDTO:stateDTOs){
            if (stateDTO.getStateType().equals("newstate"))
                newstates.add(stateDTO);
        }
        List<StateDTO> ret = new ArrayList<>();
        for(StateDTO stateDTO:newstates){
            while(stateDTO.getNextState()!= null){
                ret.add(stateDTO);
                stateDTO=stateDTO.getNextState();
            }
            if (ret.size()==0){
                ret.clear();
            } else if (ret.get(ret.size()-1).getStateId() == latestStateId) {
                return ret;
            }
        }
        return ret;
    }
}
