package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StateEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository that allows operations with the states of applications in database.
 */
public interface StatesRepository extends CrudRepository<StateEntity, Long> {

    /**
     * Returns the states of an application in descending order.
     *
     * @param applicationEntity the application
     * @return the list of states of the application
     */
    List<StateEntity> findByApplicationEntityOrderByStateIndexDesc(
        ApplicationEntity applicationEntity);

    /**
     * Returns the newest state of the application.
     *
     * @param applicationEntity the application
     * @return the newest state of the application
     */
    StateEntity findTopByApplicationEntityOrderByStateIndexDesc(
        ApplicationEntity applicationEntity);
}
