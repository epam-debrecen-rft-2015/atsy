package com.epam.rft.atsy.persistence.repositories;


import com.epam.rft.atsy.persistence.configuration.PersistenceConfiguration;
import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration")
@ContextConfiguration(classes = PersistenceConfiguration.class, initializers = RepositoryITApplciationContextInitializer.class)
@Transactional
@Sql("classpath:sql/application/application.sql")
public class ApplicationsRepositoryIT {

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
