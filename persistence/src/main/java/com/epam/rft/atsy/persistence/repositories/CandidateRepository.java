package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<CandidateEntity, Long> {
}
