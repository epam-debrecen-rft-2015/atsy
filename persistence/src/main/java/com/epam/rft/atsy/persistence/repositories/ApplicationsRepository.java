package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository that allows operations with applications in database.
 */
public interface ApplicationsRepository extends JpaRepository<ApplicationEntity, Long> {

  /**
   * Returns the list of ApplicationEntities of the candidate.
   * @param candidateEntity the given candidate
   * @return the list of ApplicationEntities
   */
  List<ApplicationEntity> findByCandidateEntity(CandidateEntity candidateEntity);
}
