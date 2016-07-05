package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private CandidateRepository candidateRepository;

    private final static Type CANDIDATEDTO_LIST_TYPE = new TypeToken<List<CandidateDTO>>() {}.getType();

    @Override
    public CandidateDTO getCandidate(Long id) {
        CandidateEntity candidateEntity = candidateRepository.findOne(id);
        return modelMapper.map(candidateEntity, CandidateDTO.class);
    }

    @Override
    public Collection<CandidateDTO> getAllCandidate(FilterRequest sortingRequest) {
        SearchOptions searchOptions =sortingRequest.getSearchOptions();
        Collection<CandidateEntity> candidateEntities = candidateRepository.findAllCandidatesByFilterRequest(
                searchOptions.getName(),searchOptions.getEmail(), searchOptions.getPhone()
                ,new Sort(Sort.Direction.fromString(sortingRequest.getOrder().name()),sortingRequest.getFieldName()));
        return modelMapper.map(candidateEntities, CANDIDATEDTO_LIST_TYPE);
    }

    @Override
    public Long saveOrUpdate(CandidateDTO candidate) {
        Assert.notNull(candidate);
        CandidateEntity entity = modelMapper.map(candidate, CandidateEntity.class);
        try {
            return candidateRepository.save(entity).getId();
        } catch (ConstraintViolationException | DataIntegrityViolationException constraint) {
            throw new DuplicateRecordException(candidate.getName());
        }
    }
}
