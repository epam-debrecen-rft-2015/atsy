package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.CandidateDAO;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.request.SortingRequest;
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
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by tothd on 2015. 11. 07..
 */
@Service
public class CandidateServiceImpl implements CandidateService {
    @Resource
    CandidateDAO candidateDAO;

    @Resource
    ModelMapper modelMapper;

    @Override
    public CandidateDTO getCandidate(Long id){
        CandidateDTO candidateDTO;
        CandidateEntity candidateEntity = candidateDAO.load(id);
        candidateDTO=modelMapper.map(candidateEntity,CandidateDTO.class);
        return candidateDTO;
    }

    @Override
    public Collection<CandidateDTO> getAllCandidate(SortingRequest sortingRequest) {
        Collection<CandidateEntity> candidateEntities = candidateDAO.loadAll(sortingRequest);
        Type targetListType = new TypeToken<List<CandidateDTO>>() {
        }.getType();
        return modelMapper.map(candidateEntities, targetListType);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void saveOrUpdate(CandidateDTO candidate) {
        Assert.notNull(candidate);
        CandidateEntity entity = modelMapper.map(candidate, CandidateEntity.class);
        try {
            if (entity.getCandidateId() == null) {
                candidateDAO.create(entity);
            } else {
                candidateDAO.update(entity);
}
        } catch (ConstraintViolationException | DataIntegrityViolationException constraint) {
            throw new DuplicateRecordException(candidate.getName());
        }
    }
}
