package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationsRepository extends JpaRepository<ApplicationEntity, Long> {

    List<ApplicationEntity> findByCandidateEntity(CandidateEntity candidateEntity);
}
