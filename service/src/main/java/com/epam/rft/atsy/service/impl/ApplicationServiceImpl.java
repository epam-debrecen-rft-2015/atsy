package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationRepository;
import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateViewDTO;
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
        Collection<StateEntity> stateEntities = applicationRepository.findByCandidateIdOrderByApplicationIdAscStateIndexAsc(id);
        Type targetListType = new TypeToken<List<StateDTO>>() {
        }.getType();

        List<CandidateApplicationDTO> candidateApplicationDTOList=new LinkedList<>();
        List<StateDTO> stateDTOs=modelMapper.map(stateEntities, targetListType);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        CandidateApplicationDTO candidateApplicationDTO = null;
        for(int i=0;i<stateDTOs.size();i++){
            if(stateDTOs.get(i).getStateIndex()==0){
                candidateApplicationDTO=new CandidateApplicationDTO();
                candidateApplicationDTO.setCreationDate(simpleDateFormat.format(stateDTOs.get(i).getCreationDate()));
                candidateApplicationDTO.setApplicationId(stateDTOs.get(i).getApplicationId());
            }

            if(i+1<stateDTOs.size() && stateDTOs.get(i+1).getApplicationId() != stateDTOs.get(i).getApplicationId()){
                convertStateDTOToApplicationDTO(candidateApplicationDTOList, stateDTOs, simpleDateFormat, candidateApplicationDTO, i);
            }
            if(i+1>=stateDTOs.size()){
                convertStateDTOToApplicationDTO(candidateApplicationDTOList, stateDTOs, simpleDateFormat, candidateApplicationDTO, i);
            }
        }
        return candidateApplicationDTOList;
    }

    private void convertStateDTOToApplicationDTO(List<CandidateApplicationDTO> candidateApplicationDTOList, List<StateDTO> stateDTOs, SimpleDateFormat simpleDateFormat, CandidateApplicationDTO candidateApplicationDTO, int i) {
        candidateApplicationDTO.setStateType(stateDTOs.get(i).getStateType());
        candidateApplicationDTO.setPositionName(stateDTOs.get(i).getPosition().getName());
        candidateApplicationDTO.setLastStateId(stateDTOs.get(i).getStateId());
        candidateApplicationDTO.setModificationDate(simpleDateFormat.format(stateDTOs.get(i).getCreationDate()));

        candidateApplicationDTOList.add(candidateApplicationDTO);
    }

    @Override
    public Long saveState(StateDTO state) {
        StateEntity stateEntity = modelMapper.map(state, StateEntity.class);

        stateEntity.setCreationDate(new Date());

        return applicationRepository.save(stateEntity).getStateId();
    }

    @Override
    public Long getNewApplicationId(){
        return applicationRepository.getMaxApplicationId() != null ? applicationRepository.getMaxApplicationId()+1 : 0;
    }

    @Override
    public List<StateViewDTO> getStatesByApplicationId(Long applicationId) {
        List<StateEntity> stateEntities = applicationRepository.findByApplicationIdOrderByStateIndexDesc(applicationId);
        Type targetListType = new TypeToken<List<StateViewDTO>>() {
        }.getType();
        List<StateViewDTO> stateDTOs=modelMapper.map(stateEntities, targetListType);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(int i=0;i<stateDTOs.size();i++){
            stateDTOs.get(i).setCreationDate(simpleDateFormat.format(stateEntities.get(i).getCreationDate()));
        }
        return stateDTOs;
    }
}
