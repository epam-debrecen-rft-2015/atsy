package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity;
import com.epam.rft.atsy.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository that allows operations with the password history in database.
 */
public interface PasswordHistoryRepository extends JpaRepository<PasswordHistoryEntity,Long> {

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
     * @param id the user's id
     * @return the oldest password
     */
    @Query(value = "SELECT * FROM atsy.password_history WHERE users_id=? ORDER BY change_date LIMIT 1", nativeQuery = true)
    PasswordHistoryEntity findOldestPassword(Long id);
}
