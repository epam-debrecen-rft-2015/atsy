package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.CandidateDAO;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.persistence.request.CandidateRequestDTO;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public Collection<CandidateDTO> getAllCandidate(CandidateRequestDTO candidateRequestDTO) {
        Collection<CandidateEntity> positionEntities = candidateDAO.loadAll(candidateRequestDTO);
        Type targetListType = new TypeToken<List<CandidateDTO>>() {
        }.getType();
        return modelMapper.map(positionEntities, targetListType);
    }
}
