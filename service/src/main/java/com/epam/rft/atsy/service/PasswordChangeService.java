package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PasswordHistoryDTO;

import java.util.List;

/**
 * Service that operates with password changes in the database layer and in the view layer.
 */
public interface PasswordChangeService {

    /**
     * Saves a password history to the database and returns it's id.
     *
     * @param passwordHistoryDTO the password history
     * @return the id of password history
     */
    Long saveOrUpdate(PasswordHistoryDTO passwordHistoryDTO);

    /**
     * Returns a list of old passwords of the given user.
     *
     * @param id the user's id
     * @return the list of old passwords
     */
    List<String> getOldPasswords(Long id);

    /**
     * Deletes the oldest password of the given user.
     *
     * @param userId the user's id
     */
    void deleteOldestPassword(Long userId);
}
