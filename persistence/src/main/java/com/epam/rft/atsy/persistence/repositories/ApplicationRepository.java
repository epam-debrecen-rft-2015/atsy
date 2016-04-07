package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.states.StateEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<StateEntity, Long> {

    List<StateEntity> findByCandidateId(Long candidateId);

    @Query(value = "select max(application_id) from States", nativeQuery = true)
    Long getMaxApplicationId();

    List<StateEntity> findByCandidateIdOrderByApplicationIdAscStateIndexAsc(Long candidateId);

    List<StateEntity> findByApplicationIdOrderByStateIndexDesc(Long applicationId);
}
