package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private StatesService statesService;

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PositionRepository positionRepository;


    @Override
    public Long saveOrUpdate(ApplicationDTO applicationDTO) {
        ApplicationEntity applicationEntity = modelMapper.map(applicationDTO, ApplicationEntity.class);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(applicationDTO.getCandidateId()));
        applicationEntity.setPositionEntity(positionRepository.findOne(applicationDTO.getPositionId()));
        return applicationsRepository.save(applicationEntity).getApplicationId();
    }

}
