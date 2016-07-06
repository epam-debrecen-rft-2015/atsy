package com.epam.rft.atsy.persistence.repositories;


import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql("classpath:sql/application/application.sql")
public class ApplicationsRepositoryIT extends AbstractRepositoryIT {

    @Autowired
    private ApplicationsRepository repository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void findByCandidateEntityShouldNotFindCandidatesByUnknownCandidate() {

        CandidateEntity candidate = this.candidateRepository.findOne(2L);
        List<ApplicationEntity> result = this.repository.findByCandidateEntity(candidate);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

}
