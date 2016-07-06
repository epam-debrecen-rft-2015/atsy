package com.epam.rft.atsy.persistence.repositories;


import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Sql("classpath:sql/application/application.sql")
public class ApplicationsRepositoryIT extends AbstractRepositoryIT {

    public static final long CANDIDATE_A_ID = 1L;
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

    @Test
    public void findByCandidateEntityShouldFindSingleApplicationForCandidateWithSingleApplication() {
        // Given
        CandidateEntity candidateEntityA = this.candidateRepository.findOne(CANDIDATE_A_ID);
        ChannelEntity expectedChannelEntity = ChannelEntity.builder()
                .id(1L)
                .name("direkt")
                .build();
        PositionEntity expectedPositionEntity = PositionEntity.builder()
                .id(1L)
                .name("Fejlesztő")
                .build();
        Date currentDate = new Date();

        // When
        List<ApplicationEntity> result = this.repository.findByCandidateEntity(candidateEntityA);

        // Then
        assertThat(result, notNullValue());
        assertThat(result.size(), is(1));

        ApplicationEntity application = result.get(0);
        assertThat(application, notNullValue());

        assertThat(application.getCandidateEntity(), notNullValue());
        assertThat(application.getCandidateEntity(), is(candidateEntityA));

        assertThat(application.getChannelEntity(), notNullValue());
        assertThat(application.getChannelEntity(), is(expectedChannelEntity));

        assertThat(application.getPositionEntity(), notNullValue());
        assertThat(application.getPositionEntity(), is(expectedPositionEntity));

        assertThat(application.getCreationDate(), notNullValue());
        assertThat(application.getCreationDate(), lessThanOrEqualTo(currentDate));
    }


}
