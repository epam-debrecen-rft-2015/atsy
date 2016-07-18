package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository that allows operations with the password history in database.
 */
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistoryEntity, Long> {

  /**
   * Returns the list of PasswordHistoryEntities of the user.
   *
   * @param userEntity the user
   * @return the list of PasswordHistories of the user
   */
  List<PasswordHistoryEntity> findByUserEntity(UserEntity userEntity);

  /**
   * Returns the oldest password of the user.
   *
   * @param userId the user's id
   * @return the oldest password
   */
  PasswordHistoryEntity findTop1ByUserEntityIdOrderByChangeDate(Long userId);

}
