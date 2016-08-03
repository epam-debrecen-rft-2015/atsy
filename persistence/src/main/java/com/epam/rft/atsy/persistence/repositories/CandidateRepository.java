package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

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

  /**
   * Returns the candidate whose email is equal to the specified email.
   * @param email the email of the candidate we're searching for
   * @return the candidate
   */
  CandidateEntity findByEmail(String email);
}
