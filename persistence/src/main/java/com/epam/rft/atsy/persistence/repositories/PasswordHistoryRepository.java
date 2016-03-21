package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistoryEntity,Long> {

    List<PasswordHistoryEntity> findByUserEntity(UserEntity userEntity);
}
