/*package com.epam.rft.atsy.persistence.repositories;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Date;
import java.util.List;*/

/**
 * Created by Gabor_Ivanyi-Nagy on 7/7/2016.
 */

/*@Sql("classpath:sql/states/states.sql")
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
  private StatesHistoryRepository statesHistoryRepository;
  @Autowired
  private CandidateRepository candidateRepository;

  @Test
  public void findTopByApplicationEntityOrderByStateIndexDescShouldNotFindStateForApplicationWithoutStates() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_A);

    // When
    StatesHistoryEntity
        statesHistoryEntity =
        this.statesHistoryRepository.findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertNull(statesHistoryEntity);
  }

  @Test
  public void findByApplicationEntityOrderByStateIndexDescShouldNotFindStateForApplicationWithoutStates() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_A);

    // When
    List<StatesHistoryEntity>
        statesHistoryEntityList =
        this.statesHistoryRepository.findByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertThat(statesHistoryEntityList, notNullValue());
    assertThat(statesHistoryEntityList, empty());
  }

  @Test
  public void findTopByApplicationEntityOrderByStateIndexDescShouldFindSingleStateForApplicationWithSingleState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_B);
    StatesHistoryEntity expectedStatesHistoryEntity = getExpectedStateEntityForSingleState();

    // When
    StatesHistoryEntity
        statesHistoryEntity =
        this.statesHistoryRepository.findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertStateEntity(statesHistoryEntity, expectedStatesHistoryEntity);
  }

  @Test
  public void findByApplicationEntityOrderByStateIndexDescShouldFindSingleStateForApplicationWithSingleState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_B);
    StatesHistoryEntity expectedStatesHistoryEntity = getExpectedStateEntityForSingleState();

    // When
    List<StatesHistoryEntity>
        statesHistoryEntityList =
        this.statesHistoryRepository.findByApplicationEntityOrderByCreationDateDesc(applicationEntity);
    assertThat(statesHistoryEntityList, notNullValue());
    assertThat(statesHistoryEntityList.size(), is(VALUE_ONE));
    StatesHistoryEntity statesHistoryEntity = statesHistoryEntityList.get(ZEROTH_ELEMENT);

    // Then
    assertStateEntity(statesHistoryEntity, expectedStatesHistoryEntity);
  }

  @Test
  public void findTopByApplicationEntityOrderByStateIndexDescShouldFindThreeStateForApplicationWithThreeState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_C);
    StatesHistoryEntity
        expectedStatesHistoryEntity =
        getExpectedSortingStateEntityListWithThreeElements().get(ZEROTH_ELEMENT);

    // When
    StatesHistoryEntity
        statesHistoryEntity =
        this.statesHistoryRepository.findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertStateEntity(statesHistoryEntity, expectedStatesHistoryEntity);
  }

  @Test
  public void findByApplicationEntityOrderByStateIndexDescShouldFindThreeStateForApplicationWithThreeState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateName(CANDIDATE_NAME_C);

    // When
    List<StatesHistoryEntity>
        statesHistoryEntityList =
        this.statesHistoryRepository.findByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertThat(statesHistoryEntityList, notNullValue());
    assertThat(statesHistoryEntityList.size(), is(VALUE_THREE));
    checkSortingValidationWithThreeElements(statesHistoryEntityList);
  }

  private void checkSortingValidationWithThreeElements(List<StatesHistoryEntity> statesHistoryEntityList) {
    List<StatesHistoryEntity>
        expectedStatesHistoryEntityList =
        getExpectedSortingStateEntityListWithThreeElements();

    assertThat(statesHistoryEntityList, notNullValue());
    assertThat(expectedStatesHistoryEntityList, notNullValue());
    assertThat(statesHistoryEntityList.size(), is(expectedStatesHistoryEntityList.size()));

    for (int i = 0; i < expectedStatesHistoryEntityList.size(); ++i) {
      assertStateEntity(statesHistoryEntityList.get(i), expectedStatesHistoryEntityList.get(i));
    }
  }

  private StatesHistoryEntity getExpectedStateEntityForSingleState() {
    return StatesHistoryEntity.builder()
        .applicationEntity(
            getExpectedApplicationEntity(CANDIDATE_NAME_B, FIRST_ID, CHANNEL_NAME_DIRECT, FIRST_ID,
                POSITION_NAME_DEVELOPER))
        .stateIndex(VALUE_ONE)
        .build();
  }

  private List<StatesHistoryEntity> getExpectedSortingStateEntityListWithThreeElements() {
    List<StatesHistoryEntity> statesHistoryEntityList = Arrays.asList(StatesHistoryEntity.builder()
            .applicationEntity(
                getExpectedApplicationEntity(CANDIDATE_NAME_C, FOURTH_ID, CHANNEL_NAME_FACEBOOK,
                    FIRST_ID, POSITION_NAME_DEVELOPER))
            .stateIndex(BIGGEST_STATE_INDEX_NUMBER)
            .build(),

        StatesHistoryEntity.builder()
            .applicationEntity(
                getExpectedApplicationEntity(CANDIDATE_NAME_C, FOURTH_ID, CHANNEL_NAME_FACEBOOK,
                    FIRST_ID, POSITION_NAME_DEVELOPER))
            .stateIndex(MIDDLE_STATE_INDEX_NUMBER)
            .build(),
        StatesHistoryEntity.builder()
            .applicationEntity(
                getExpectedApplicationEntity(CANDIDATE_NAME_C, FOURTH_ID, CHANNEL_NAME_FACEBOOK,
                    FIRST_ID, POSITION_NAME_DEVELOPER))
            .stateIndex(SMALLEST_STATE_INDEX_NUMBER)
            .build()
    );

    return statesHistoryEntityList;
  }

  private ApplicationEntity getApplicationByCandidateName(String candidateName) {
    // Given
    CandidateEntity candidateEntity = this.candidateRepository.findByName(candidateName);
    return this.applicationsRepository.findByCandidateEntity(candidateEntity).get(ZEROTH_ELEMENT);
  }

  private ApplicationEntity getExpectedApplicationEntity(String candidateName, Long channelId,
                                                         String channelName, Long positionId,
                                                         String positionName) {
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

    Long
        expectedApplicationId =
        this.applicationsRepository.findByCandidateEntity(candidateEntity).get(ZEROTH_ELEMENT)
            .getId();
    ApplicationEntity expectedApplicationEntity = ApplicationEntity.builder()
        .id(expectedApplicationId)
        .positionEntity(expectedPositionEntity)
        .channelEntity(expectedChannelEntity)
        .candidateEntity(candidateEntity)
        .creationDate(currentDate)
        .build();

    return expectedApplicationEntity;
  }

  private void assertStateEntity(StatesHistoryEntity statesHistoryEntity, StatesHistoryEntity expectedStatesHistoryEntity) {
    assertThat(statesHistoryEntity, notNullValue());
    assertThat(expectedStatesHistoryEntity, notNullValue());

    assertThat(statesHistoryEntity.getStateIndex(), notNullValue());
    assertThat(expectedStatesHistoryEntity.getStateIndex(), notNullValue());
    assertThat(statesHistoryEntity.getStateIndex(), is(expectedStatesHistoryEntity.getStateIndex()));

    assertThat(statesHistoryEntity.getApplicationEntity(), notNullValue());
    assertThat(expectedStatesHistoryEntity.getApplicationEntity(), notNullValue());
    assertApplicationEntity(statesHistoryEntity.getApplicationEntity(),
        expectedStatesHistoryEntity.getApplicationEntity());
  }

  private void assertApplicationEntity(ApplicationEntity application,
                                       ApplicationEntity expectedApplicationEntity) {
    assertThat(application, notNullValue());
    assertThat(expectedApplicationEntity, notNullValue());

    // Id test
    assertThat(application.getId(), notNullValue());
    assertThat(expectedApplicationEntity.getId(), notNullValue());
    assertThat(application.getId(), is(expectedApplicationEntity.getId()));

    // Candidate entity test
    assertThat(application.getCandidateEntity(), notNullValue());
    assertThat(expectedApplicationEntity.getCandidateEntity(), notNullValue());
    assertThat(application.getCandidateEntity(),
        is(expectedApplicationEntity.getCandidateEntity()));

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
    assertThat(application.getCreationDate(),
        lessThanOrEqualTo(expectedApplicationEntity.getCreationDate()));
  }
}*/