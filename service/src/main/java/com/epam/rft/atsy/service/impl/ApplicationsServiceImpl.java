package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.ApplicationService;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ApplicationsServiceImpl implements ApplicationsService {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private ApplicationService applicationService;

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Long saveOrUpdate(ApplicationDTO applicationDTO) {
        ApplicationEntity applicationEntity = modelMapper.map(applicationDTO, ApplicationEntity.class);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(applicationDTO.getCandidateId()));
        return applicationsRepository.save(applicationEntity).getApplicationId();
    }

}
