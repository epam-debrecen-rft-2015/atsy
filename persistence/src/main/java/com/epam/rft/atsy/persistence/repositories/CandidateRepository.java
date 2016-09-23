package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository that allows operations with candidates in database.
 */
public interface CandidateRepository extends LogicallyDeletableRepository<CandidateEntity, Long> {

  /**
   * Returns the list of non-deleted CandidateEntities whom suits the given conditions.
   * @param candidateName the name, which the candidate's name must contain
   * @param candidateEmail the email, which the candidate's email must contain
   * @param candidatePhone the phone number, which the candidate's phone number must contain
   * @param candidatePosition the position, which the candidate's phone number must contain
   * @return the list of CandidateEntities
   */
  @Query(value = "SELECT DISTINCT candidate FROM ApplicationEntity application "
      + "RIGHT OUTER JOIN application.candidateEntity candidate "
      + "LEFT OUTER JOIN application.positionEntity position "
      + "WHERE candidate.deleted = false AND "
      + "candidate.name LIKE CONCAT('%',:name,'%') AND "
      + "candidate.email LIKE CONCAT('%',:email,'%') AND "
      + "candidate.phone LIKE CONCAT('%',:phone,'%') AND "
      + "(position.name LIKE CONCAT('%',:position,'%') OR LENGTH(TRIM(:position)) < 1) ")
  Page<CandidateEntity> findByCandidateFilterRequest(@Param("name") String candidateName,
                                                     @Param("email") String candidateEmail,
                                                     @Param("phone") String candidatePhone,
                                                     @Param("position") String candidatePosition,
                                                     Pageable pageable);

  /**
   * Returns the candidate whose email is equal to the specified email.
   * @param email the email of the candidate we're searching for
   * @return the candidate
   */
  CandidateEntity findByEmail(String email);
}
