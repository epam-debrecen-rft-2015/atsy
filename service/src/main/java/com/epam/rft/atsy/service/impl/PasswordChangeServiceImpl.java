package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.PasswordHistoryRepository;
import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;

    @Override
    public Long saveOrUpdate(PasswordHistoryDTO passwordHistoryDTO) {
        Assert.notNull(passwordHistoryDTO);
        PasswordHistoryEntity entity = modelMapper.map(passwordHistoryDTO, PasswordHistoryEntity.class);
        try {
            return passwordHistoryRepository.save(entity).getChangeId();
        } catch (ConstraintViolationException | DataIntegrityViolationException constraint) {
            throw new DuplicateRecordException("alma");
        }
    }
}
