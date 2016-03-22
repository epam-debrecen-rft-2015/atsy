package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasswordHistoryRepository extends JpaRepository<PasswordHistoryEntity,Long> {

    List<PasswordHistoryEntity> findByUserEntity(UserEntity userEntity);

    @Query(value = "SELECT * FROM atsy.PasswordHistory WHERE userid=? ORDER BY change_date LIMIT 1", nativeQuery = true)
    PasswordHistoryEntity findOldestPassword(Long id);
}
