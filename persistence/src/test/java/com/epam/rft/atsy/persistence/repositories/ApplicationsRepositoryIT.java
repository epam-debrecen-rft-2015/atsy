package com.epam.rft.atsy.persistence.repositories;


import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Sql("classpath:sql/application/application.sql")
public class ApplicationsRepositoryIT extends AbstractRepositoryIT {

    public static final long CANDIDATE_B_ID = 2L;

    @Autowired
    private ApplicationsRepository repository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void findByCandidateEntityShouldNotFindApplicationForCandidateWithoutApplications() {
        // Given
        CandidateEntity candidateB = this.candidateRepository.findOne(CANDIDATE_B_ID);

        // When
        List<ApplicationEntity> result = this.repository.findByCandidateEntity(candidateB);

        // Then
        assertThat(result, notNullValue());
        assertThat(result, empty());
    }

}
