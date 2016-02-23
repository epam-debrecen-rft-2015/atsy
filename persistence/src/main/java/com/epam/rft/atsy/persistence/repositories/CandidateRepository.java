package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<CandidateEntity, Long> {

    /*@Query("SELECT s FROM StateEntity s JOIN s.candidate WHERE id = ")
    List<StateEntity> alma(@Param("alma2") Long candidateId);*/
}
