package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/7/2016.
 */

@Sql("classpath:sql/states/states.sql")
public class StatesRepositoryIT extends AbstractRepositoryIT {

    public static final String CANDIDATE_NAME_A = "Candidate A";
    public static final String CANDIDATE_NAME_B = "Candidate B";
    public static final String CANDIDATE_NAME_C = "Candidate C";

    public static final String CHANNEL_NAME_DIRECT = "direkt";
    public static final String CHANNEL_NAME_FACEBOOK = "facebook";
    public static final String POSITION_NAME_DEVELOPER = "Fejlesztő";

    public static final int ZEROTH_ELEMENT = 0;
    public static final int VALUE_ONE = 1;
    public static final int VALUE_THREE = 3;

    public static final int BIGGEST_STATE_INDEX_NUMBER = 5;
    public static final int MIDDLE_STATE_INDEX_NUMBER = 3;
    public static final int SMALLEST_STATE_INDEX_NUMBER = 1;

    public static final long FIRST_ID = 1L;
    public static final long FOURTH_ID = 4L;

    @Autowired
    private ApplicationsRepository applicationsRepository;
    @Autowired
    private StatesRepository statesRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void findTopByApplicationEntityOrderByStateIndexDescShouldNotFindStateForApplicationWithoutStates() {
        // Given
        ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_A);

        // When
        StateEntity stateEntity = this.statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);

        // Then
        assertNull(stateEntity);
    }

    @Test
    public void findByApplicationEntityOrderByStateIndexDescShouldNotFindStateForApplicationWithoutStates() {
        // Given
        ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_A);

        // When
        List<StateEntity> stateEntityList = this.statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity);

        // Then
        assertThat(stateEntityList, notNullValue());
        assertThat(stateEntityList, empty());
    }

    @Test
    public void findTopByApplicationEntityOrderByStateIndexDescShouldFindSingleStateForApplicationWithSingleState() {
        // Given
        ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_B);
        StateEntity expectedStateEntity = getExpectedStateEntityForSingleState();

        // When
        StateEntity stateEntity = this.statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);

        // Then
        assertStateEntity(stateEntity, expectedStateEntity);
    }

    @Test
    public void findByApplicationEntityOrderByStateIndexDescShouldFindSingleStateForApplicationWithSingleState() {
        // Given
        ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_B);
        StateEntity expectedStateEntity = getExpectedStateEntityForSingleState();

        // When
        List<StateEntity> stateEntityList = this.statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity);
        assertThat(stateEntityList, notNullValue());
        assertThat(stateEntityList.size(), is(VALUE_ONE));
        StateEntity stateEntity = stateEntityList.get(ZEROTH_ELEMENT);

        // Then
        assertStateEntity(stateEntity, expectedStateEntity);
    }

    @Test
    public void findTopByApplicationEntityOrderByStateIndexDescShouldFindThreeStateForApplicationWithThreeState() {
        // Given
        ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_C);
        StateEntity expectedStateEntity = getExpectedSortingStateEntityListWithThreeElements().get(ZEROTH_ELEMENT);

        // When
        StateEntity stateEntity = this.statesRepository.findTopByApplicationEntityOrderByStateIndexDesc(applicationEntity);

        // Then
        assertStateEntity(stateEntity, expectedStateEntity);
    }

    @Test
    public void findByApplicationEntityOrderByStateIndexDescShouldFindThreeStateForApplicationWithThreeState() {
        // Given
        ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_C);

        // When
        List<StateEntity> stateEntityList = this.statesRepository.findByApplicationEntityOrderByStateIndexDesc(applicationEntity);

        // Then
        assertThat(stateEntityList, notNullValue());
        assertThat(stateEntityList.size(), is(VALUE_THREE));
        checkSortingValidationWithThreeElements(stateEntityList);
    }

    private void checkSortingValidationWithThreeElements(List<StateEntity> stateEntityList) {
        List<StateEntity> expectedStateEntityList = getExpectedSortingStateEntityListWithThreeElements();

        assertThat(stateEntityList, notNullValue());
        assertThat(expectedStateEntityList, notNullValue());
        assertThat(stateEntityList.size(), is(expectedStateEntityList.size()));

        for (int i = 0; i < expectedStateEntityList.size(); ++i) {
            assertStateEntity(stateEntityList.get(i), expectedStateEntityList.get(i));
        }
    }

    private StateEntity getExpectedStateEntityForSingleState() {
        return StateEntity.builder()
                .applicationEntity(getExpectedApplicationEntity(CANDIDATE_NAME_B, FIRST_ID, CHANNEL_NAME_DIRECT, FIRST_ID, POSITION_NAME_DEVELOPER))
                .stateIndex(VALUE_ONE)
                .build();
    }

    private List<StateEntity> getExpectedSortingStateEntityListWithThreeElements() {
        List<StateEntity> stateEntityList = Arrays.asList(StateEntity.builder()
                        .applicationEntity(getExpectedApplicationEntity(CANDIDATE_NAME_C, FOURTH_ID, CHANNEL_NAME_FACEBOOK, FIRST_ID, POSITION_NAME_DEVELOPER))
                        .stateIndex(BIGGEST_STATE_INDEX_NUMBER)
                        .build(),

                StateEntity.builder()
                        .applicationEntity(getExpectedApplicationEntity(CANDIDATE_NAME_C, FOURTH_ID, CHANNEL_NAME_FACEBOOK, FIRST_ID, POSITION_NAME_DEVELOPER))
                        .stateIndex(MIDDLE_STATE_INDEX_NUMBER)
                        .build(),
                StateEntity.builder()
                        .applicationEntity(getExpectedApplicationEntity(CANDIDATE_NAME_C, FOURTH_ID, CHANNEL_NAME_FACEBOOK, FIRST_ID, POSITION_NAME_DEVELOPER))
                        .stateIndex(SMALLEST_STATE_INDEX_NUMBER)
                        .build()
        );

        return stateEntityList;
    }

    private ApplicationEntity getApplicationByCandidateName(String candidateName) {
        // Given
        CandidateEntity candidateEntity = this.candidateRepository.findByName(candidateName);
        return this.applicationsRepository.findByCandidateEntity(candidateEntity).get(ZEROTH_ELEMENT);
    }

    private ApplicationEntity getExpectedApplicationEntity(String candidateName, Long channelId, String channelName, Long positionId, String positionName) {
        CandidateEntity candidateEntity = this.candidateRepository.findByName(candidateName);

        ChannelEntity expectedChannelEntity = ChannelEntity.builder()
                .id(channelId)
                .name(channelName)
                .build();

        PositionEntity expectedPositionEntity = PositionEntity.builder()
                .id(positionId)
                .name(positionName)
                .build();

        Date currentDate = new Date();

        Long expectedApplicationId = this.applicationsRepository.findByCandidateEntity(candidateEntity).get(ZEROTH_ELEMENT).getId();
        ApplicationEntity expectedApplicationEntity = ApplicationEntity.builder()
                .id(expectedApplicationId)
                .positionEntity(expectedPositionEntity)
                .channelEntity(expectedChannelEntity)
                .candidateEntity(candidateEntity)
                .creationDate(currentDate)
                .build();

        return expectedApplicationEntity;
    }

    private void assertStateEntity(StateEntity stateEntity, StateEntity expectedStateEntity) {
        assertThat(stateEntity, notNullValue());
        assertThat(expectedStateEntity, notNullValue());

        assertThat(stateEntity.getStateIndex(), notNullValue());
        assertThat(expectedStateEntity.getStateIndex(), notNullValue());
        assertThat(stateEntity.getStateIndex(), is(expectedStateEntity.getStateIndex()));

        assertThat(stateEntity.getApplicationEntity(), notNullValue());
        assertThat(expectedStateEntity.getApplicationEntity(), notNullValue());
        assertApplicationEntity(stateEntity.getApplicationEntity(), expectedStateEntity.getApplicationEntity());
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