package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository that allows operations with the state histories of applications in database.
 */
public interface StatesHistoryRepository extends JpaRepository<StatesHistoryEntity, Long> {

  /**
   * Returns the state histories of an application in descending order.
   * @param applicationEntity the application
   * @return the list of states of the application
   */
  List<StatesHistoryEntity> findByApplicationEntityOrderByCreationDateDesc(
      ApplicationEntity applicationEntity);

  /**
   * Returns the newest state history entity of the application.
   * @param applicationEntity the application
   * @return the newest state of the application
   */
  StatesHistoryEntity findTopByApplicationEntityOrderByCreationDateDesc(
      ApplicationEntity applicationEntity);
}
