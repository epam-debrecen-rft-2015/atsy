package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository that allows operations with candidates in database.
 */
public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

  /**
   * Returns the list of CandidateEntities whom suits the given conditions.
   * @param name the name, which the candidate's name must contain
   * @param email the email, which the candidate's email must contain
   * @param phone the phone number, which the candidate's phone number must contain
   * @param sort the sort, that sorts the list
   * @return the list of CandidateEntities
   */
  List<CandidateEntity> findAllByNameContainingAndEmailContainingAndPhoneContaining(String name,
                                                                                    String email,
                                                                                    String phone,
                                                                                    Sort sort);

  // TODO: Decide which query to use
  //  @Query(value = "SELECT DISTINCT candidate FROM CandidateEntity candidate "
//      + "WHERE candidate.name LIKE CONCAT('%',:name,'%') AND "
//      + "candidate.email LIKE CONCAT('%',:email,'%') AND "
//      + "candidate.phone LIKE CONCAT('%',:phone,'%') AND "
//      + "(candidate.id IN "
//      + "(SELECT candidateEntity FROM ApplicationEntity application "
//      + "WHERE candidate.id = application.candidateEntity AND application.positionEntity IN "
//      + "(SELECT id FROM PositionEntity position WHERE position.name LIKE CONCAT('%',:position,'%'))) "
//      + "OR "
//      + "(LENGTH(TRIM(:position)) < 1)) ")

//  SELECT distinct c.*
//  FROM candidates c
//  LEFT JOIN applications a
//  ON c.id = a.candidate_id
//  LEFT JOIN positions p
//  ON a.position_id = p.id and p.name LIKE CONCAT('%%')
//  WHERE c.name LIKE CONCAT('%%') AND
//  c.email LIKE CONCAT('%%') AND
//  c.phone LIKE CONCAT('%%');

//  @Query(value = "SELECT DISTINCT candidate FROM ApplicationEntity application "
//      + "RIGHT OUTER JOIN application.candidateEntity candidate "
//      + "LEFT OUTER JOIN application.positionEntity position "
//      + "WHERE candidate.name LIKE CONCAT('%',:name,'%') AND "
//      + "candidate.email LIKE CONCAT('%',:email,'%') AND "
//      + "candidate.phone LIKE CONCAT('%',:phone,'%') AND "
//      + "(position.name LIKE CONCAT('%',:position,'%')) OR (LENGTH(TRIM(:position)) < 1))) ")

  /**
   * Returns the list of CandidateEntities whom suits the given conditions.
   * @param candidateName the name, which the candidate's name must contain
   * @param candidateEmail the email, which the candidate's email must contain
   * @param candidatePhone the phone number, which the candidate's phone number must contain
   * @param candidatePosition the position, which the candidate's phone number must contain
   * @return the list of CandidateEntities
   */
  @Query(value = "SELECT DISTINCT candidate FROM CandidateEntity candidate "
      + "WHERE candidate.name LIKE CONCAT('%',:name,'%') AND "
      + "candidate.email LIKE CONCAT('%',:email,'%') AND "
      + "candidate.phone LIKE CONCAT('%',:phone,'%') AND "
      + "(candidate.id IN "
      + "(SELECT candidateEntity FROM ApplicationEntity application "
      + "WHERE candidate.id = application.candidateEntity AND application.positionEntity IN "
      + "(SELECT id FROM PositionEntity position "
      + "WHERE position.name LIKE CONCAT('%',:position,'%'))) OR (LENGTH(TRIM(:position)) < 1)) ")
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
