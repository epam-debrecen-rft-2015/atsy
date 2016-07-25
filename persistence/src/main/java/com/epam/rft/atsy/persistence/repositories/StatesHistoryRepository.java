package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository that allows operations with the states of applications in database.
 */
public interface StatesHistoryRepository extends CrudRepository<StatesHistoryEntity, Long> {

  /**
   * Returns the states of an application in descending order.
   * @param applicationEntity the application
   * @return the list of states of the application
   */
  List<StatesHistoryEntity> findByApplicationEntityOrderByCreationDateDesc(
      ApplicationEntity applicationEntity);

  /**
   * Returns the newest state of the application.
   * @param applicationEntity the application
   * @return the newest state of the application
   */
  StatesHistoryEntity findTopByApplicationEntityOrderByCreationDateDesc(ApplicationEntity applicationEntity);
}
