package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.CandidateDAO;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.request.FilterRequest;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Resource
    private CandidateDAO candidateDAO;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public CandidateDTO getCandidate(Long id) {
        CandidateDTO candidateDTO;
        CandidateEntity candidateEntity = candidateDAO.read(id);
        candidateDTO = modelMapper.map(candidateEntity, CandidateDTO.class);
        return candidateDTO;
    }

    @Override
    public Collection<CandidateDTO> getAllCandidate(FilterRequest sortingRequest) {
        Collection<CandidateEntity> candidateEntities = candidateDAO.loadAll(sortingRequest);
        Type targetListType = new TypeToken<List<CandidateDTO>>() {
        }.getType();
        return modelMapper.map(candidateEntities, targetListType);
    }

    @Override
    public Long saveOrUpdate(CandidateDTO candidate) {
        Assert.notNull(candidate);
        Long candidateId = 0l;
        CandidateEntity entity = modelMapper.map(candidate, CandidateEntity.class);
        try {
            if (entity.getCandidateId() == null) {
                candidateId = candidateDAO.create(entity).getCandidateId();
            } else {
                candidateId = candidateDAO.update(entity).getCandidateId();
            }
        } catch (ConstraintViolationException | DataIntegrityViolationException constraint) {
            throw new DuplicateRecordException(candidate.getName());
        }
        return candidateId;
    }
}
