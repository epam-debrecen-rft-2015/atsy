package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.request.SortingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidateRepository extends JpaRepository<CandidateEntity, Long> {

    @Query("select c from CandidateEntity c where c.name like %?1% and c.email like %?2% and c.phone like %?3% order by ?4 ASC")
    List<CandidateEntity> findAllCandidatesByFilterRequest(String name, String email, String phone, String fieldName);
}
