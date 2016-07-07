package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


/**
 * Created by Gabor_Ivanyi-Nagy on 7/7/2016.
 */

@Sql("classpath:sql/application/states.sql")
public class StatesRepositoryIT extends AbstractRepositoryIT {


    public static final String CANDIDATE_NAME_A = "Candidate A";
    public static final String CANDIDATE_NAME_B = "Candidate B";
    public static final String CANDIDATE_NAME_C = "Candidate C";

    public static final int ZEROTH_ELEMENT = 0;
    public static final int ZERO_ELEMENT = 0;
    public static final int ONE_ELEMENT = 1;

    @Autowired
    private ApplicationsRepository applicationsRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private CandidateRepository candidateRepository;


    @Test
    public void findByApplicationEntityOrderByStateIndexDescShouldNotFindStateForApplicationWithoutStates() {

        // Given
        ApplicationEntity applicationEntity = checkValidationGivenProcedureAndGetApplicationListByCandidateNameAndSizeOfApplicationList(CANDIDATE_NAME_A, ONE_ELEMENT).get(ZEROTH_ELEMENT);

        // When
        List<StateEntity> stateEntityList = this.statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity);


        // Then
        assertThat(stateEntityList, notNullValue());
        assertThat(stateEntityList.size(), is(ZERO_ELEMENT));
    }

    @Test
    public void findByApplicationEntityOrderByStateIndexDescShouldFindSingleStateForApplicationWithSingleState() {

        // Given
        ApplicationEntity applicationEntity = checkValidationGivenProcedureAndGetApplicationListByCandidateNameAndSizeOfApplicationList(CANDIDATE_NAME_B, ONE_ELEMENT).get(ZEROTH_ELEMENT);


        // When
        List<StateEntity> stateEntityList = this.statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity);

        // Then

        assertThat(stateEntityList, notNullValue());
        assertThat(stateEntityList.size(), is(ONE_ELEMENT));

        StateEntity stateEntity = stateEntityList.get(ZEROTH_ELEMENT);

        assertThat(stateEntity, notNullValue());
        assertApplicationEntity(stateEntity.getApplicationEntity(), getExpectedApplicationEntityForSingleState());
        assertThat(stateEntity.getStateIndex(), is(ONE_ELEMENT));
    }

    @Test
    public void findByApplicationEntityOrderByStateIndexDescShouldFindThreeStateForApplicationWithThreeState() {

        // Given
        List<ApplicationEntity> applicationEntityList = checkValidationGivenProcedureAndGetApplicationListByCandidateNameAndSizeOfApplicationList(CANDIDATE_NAME_C, ONE_ELEMENT);

        // When
        List<StateEntity> stateEntityList = this.statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntityList.get(ZEROTH_ELEMENT));




    }




    private List<ApplicationEntity> checkValidationGivenProcedureAndGetApplicationListByCandidateNameAndSizeOfApplicationList(String candidateName, Integer expectedSizeOfApplicationList) {
        // Given
        CandidateEntity candidateEntityB = this.candidateRepository.findByName(candidateName);
        assertThat(candidateEntityB, notNullValue());

        List<ApplicationEntity> applicationEntityList = this.applicationsRepository.findByCandidateEntity(candidateEntityB);
        assertThat(applicationEntityList, notNullValue());
        assertThat(applicationEntityList.size(), is(expectedSizeOfApplicationList));

        return applicationEntityList;
    }


    private ApplicationEntity getExpectedApplicationEntityForSingleState() {
        CandidateEntity candidateEntityB = this.candidateRepository.findByName(CANDIDATE_NAME_B);

        ChannelEntity expectedChannelEntity = ChannelEntity.builder()
                .id(1L)
                .name("direkt")
                .build();

        PositionEntity expectedPositionEntity = PositionEntity.builder()
                .id(1L)
                .name("Fejlesztő")
                .build();

        Date currentDate = new Date();


        Long expectedApplicationId = this.applicationsRepository.findByCandidateEntity(candidateEntityB).get(0).getId();
        ApplicationEntity expectedApplicationEntity = ApplicationEntity.builder()
                .id(expectedApplicationId)
                .positionEntity(expectedPositionEntity)
                .channelEntity(expectedChannelEntity)
                .candidateEntity(candidateEntityB)
                .creationDate(currentDate)
                .build();

        return expectedApplicationEntity;

    }





    private void assertStateEntityTest(StateEntity stateEntity, StateEntity exptectedStateEntity) {

    }

    private void assertApplicationEntity(ApplicationEntity application, ApplicationEntity expectedApplicationEntity) {
        assertThat(application, notNullValue());
        assertThat(expectedApplicationEntity, notNullValue());

        // Id test
        assertThat(application.getId(), notNullValue());
        assertThat(expectedApplicationEntity.getId(), notNullValue());
        assertThat(application.getId(), is(expectedApplicationEntity.getId()));

        // Candidate entity test
        assertThat(application.getCandidateEntity(), notNullValue());
        assertThat(expectedApplicationEntity.getCandidateEntity(), notNullValue());
        assertThat(application.getCandidateEntity(), is(expectedApplicationEntity.getCandidateEntity()));

        // Channel entity test
        assertThat(application.getChannelEntity(), notNullValue());
        assertThat(expectedApplicationEntity.getChannelEntity(), notNullValue());
        assertThat(application.getChannelEntity(), is(expectedApplicationEntity.getChannelEntity()));

        // Position entity test
        assertThat(application.getPositionEntity(), notNullValue());
        assertThat(expectedApplicationEntity.getPositionEntity(), notNullValue());
        assertThat(application.getPositionEntity(), is(expectedApplicationEntity.getPositionEntity()));

        // Date test
        assertThat(application.getCreationDate(), notNullValue());
        assertThat(expectedApplicationEntity.getCreationDate(), notNullValue());
        assertThat(application.getCreationDate(), lessThanOrEqualTo(expectedApplicationEntity.getCreationDate()));
    }

}
