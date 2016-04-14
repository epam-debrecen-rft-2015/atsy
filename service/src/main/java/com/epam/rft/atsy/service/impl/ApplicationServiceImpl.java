package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationRepository;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
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

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Collection<CandidateApplicationDTO> getStatesByCandidateId(Long id) {
        List<CandidateApplicationDTO> candidateApplicationDTOList=new LinkedList<>();
        List<ApplicationEntity> applicationList = applicationsRepository.findByCandidateEntity(candidateRepository.findOne(id));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (ApplicationEntity applicationEntity: applicationList){
            StateEntity stateEntity = applicationRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);

            CandidateApplicationDTO candidateApplicationDTO = new CandidateApplicationDTO();
            candidateApplicationDTO.setApplicationId(applicationEntity.getApplicationId());
            candidateApplicationDTO.setCreationDate(simpleDateFormat.format(applicationEntity.getCreationDate()));

            candidateApplicationDTO.setStateType(stateEntity.getStateType());
            candidateApplicationDTO.setPositionName(stateEntity.getPositionId().getName());
            candidateApplicationDTO.setLastStateId(stateEntity.getStateId());
            candidateApplicationDTO.setModificationDate(simpleDateFormat.format(stateEntity.getCreationDate()));

            candidateApplicationDTOList.add(candidateApplicationDTO);
        }
        return  candidateApplicationDTOList;
    }

    @Override
    public Long saveState(StateDTO state, Long applicationId) {
        StateEntity stateEntity = modelMapper.map(state, StateEntity.class);

        stateEntity.setCreationDate(new Date());
        stateEntity.setApplicationEntity(applicationsRepository.findOne(applicationId));

        return applicationRepository.save(stateEntity).getStateId();
    }


    @Override
    public List<StateViewDTO> getStatesByApplicationId(Long applicationId) {
        List<StateEntity> stateEntities = applicationRepository.findByApplicationEntityOrderByStateIndexDesc(applicationsRepository.findOne(1L));
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
