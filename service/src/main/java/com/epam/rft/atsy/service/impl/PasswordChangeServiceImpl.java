package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.PasswordHistoryRepository;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long saveOrUpdate(PasswordHistoryDTO passwordHistoryDTO) {
        Assert.notNull(passwordHistoryDTO);
        PasswordHistoryEntity entity = modelMapper.map(passwordHistoryDTO, PasswordHistoryEntity.class);

        entity.setUserEntity(userRepository.findOne(passwordHistoryDTO.getUserId()));
        try {
            return passwordHistoryRepository.save(entity).getChangeId();
        } catch (ConstraintViolationException | DataIntegrityViolationException constraint) {
            throw new DuplicateRecordException("alma");
        }
    }

    @Override
    public List<String> isUnique(Long id) {
        List<PasswordHistoryEntity> oldPasswords = passwordHistoryRepository.findByUserEntity(userRepository.findOne(id));
        List<String> passwords = new ArrayList<>();
        for(PasswordHistoryEntity pass:oldPasswords){
            passwords.add(pass.getPassword());
        }
        return passwords;
    }
}
