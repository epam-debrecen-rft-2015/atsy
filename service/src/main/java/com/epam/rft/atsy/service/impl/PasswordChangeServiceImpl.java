package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.repositories.PasswordHistoryRepository;
import com.epam.rft.atsy.persistence.repositories.UserRepository;
import com.epam.rft.atsy.service.PasswordChangeService;
import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PasswordChangeServiceImpl implements PasswordChangeService {

    private static final int PASSWORD_HISTORY_LIMIT = 5;

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
            Long retId = passwordHistoryRepository.save(entity).getId();
            if(passwordHistoryRepository.findByUserEntity(entity.getUserEntity()).size() >= PASSWORD_HISTORY_LIMIT){
                deleteOldestPassword(passwordHistoryDTO.getUserId());
            }
            return retId;
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            log.error("Save to repository failed.", ex);

            String userId = passwordHistoryDTO.getUserId().toString();

            throw new DuplicateRecordException(userId,
                                               "Duplication occurred when saving password history for user with ID: "
                                               + userId, ex);
        }
    }

    @Override
    public List<String> getOldPasswords(Long id) {
        List<PasswordHistoryEntity> oldPasswords = passwordHistoryRepository.findByUserEntity(userRepository.findOne(id));
        List<String> passwords = new ArrayList<>();
        for(PasswordHistoryEntity pass:oldPasswords){
            passwords.add(pass.getPassword());
        }
        return passwords;
    }

    @Override
    public void deleteOldestPassword(Long userId) {
        PasswordHistoryEntity entity = passwordHistoryRepository.findOldestPassword(userId);
        passwordHistoryRepository.delete(entity);
    }
}
