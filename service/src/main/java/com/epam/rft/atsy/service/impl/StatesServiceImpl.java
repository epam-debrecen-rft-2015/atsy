package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateViewDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class StatesServiceImpl implements StatesService {

    public static final String DATE_FORMAT_CONSTANT = "yyyy-MM-dd HH:mm:ss";

    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private final Type STATEVIEWDTO_LIST_TYPE = new TypeToken<List<StateViewDTO>>() {}.getType();

    @Override
    public Collection<CandidateApplicationDTO> getStatesByCandidateId(Long id) {
        List<CandidateApplicationDTO> candidateApplicationDTOList=new LinkedList<>();
        List<ApplicationEntity> applicationList = applicationsRepository.findByCandidateEntity(candidateRepository.findOne(id));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

        for (ApplicationEntity applicationEntity: applicationList){
            StateEntity stateEntity = statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);

            CandidateApplicationDTO candidateApplicationDTO = new CandidateApplicationDTO();
            candidateApplicationDTO.setApplicationId(applicationEntity.getId());
            candidateApplicationDTO.setCreationDate(simpleDateFormat.format(applicationEntity.getCreationDate()));

            candidateApplicationDTO.setStateType(stateEntity.getStateType());
            candidateApplicationDTO.setPositionName(applicationEntity.getPositionEntity().getName());
            candidateApplicationDTO.setLastStateId(stateEntity.getId());
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

        return statesRepository.save(stateEntity).getId();
    }


    @Override
    public List<StateViewDTO> getStatesByApplicationId(Long applicationId) {
        List<StateEntity> stateEntities = statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationsRepository.findOne(applicationId));
        List<StateViewDTO> stateDTOs=modelMapper.map(stateEntities, STATEVIEWDTO_LIST_TYPE);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_CONSTANT);

        for(int i=0;i<stateDTOs.size();i++){
            stateDTOs.get(i).setCreationDate(simpleDateFormat.format(stateEntities.get(i).getCreationDate()));
        }
        return stateDTOs;
    }
}
