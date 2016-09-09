package com.epam.rft.atsy.persistence.repositories;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.entities.StatesHistoryEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Sql("classpath:sql/states/states.sql")
public class StatesHistoryRepositoryIT extends AbstractRepositoryIT {

  private static final String CANDIDATE_NAME_D = "Candidate D";
  private static final String CANDIDATE_NAME_E = "Candidate E";
  private static final String CANDIDATE_NAME_F = "Candidate F";

  private static final String CANDIDATE_EMAIL_D = "candidate.d@atsy.com";
  private static final String CANDIDATE_EMAIL_E = "candidate.e@atsy.com";
  private static final String CANDIDATE_EMAIL_F = "candidate.f@atsy.com";

  private static final String CHANNEL_NAME_DIRECT = "direkt";
  private static final String CHANNEL_NAME_FACEBOOK = "facebook";
  private static final String POSITION_NAME_DEVELOPER = "Fejlesztő";

  private static final int ZEROTH_ELEMENT = 0;
  private static final int VALUE_ONE = 1;
  private static final int VALUE_THREE = 3;

  private static final long FIRST_ID = 1L;
  private static final long FOURTH_ID = 4L;

  private static final String STATE_NAME_CODING = "coding";
  private static final String STATE_NAME_FIRST_TEST = "firstTest";
  private static final String STATE_NAME_HR = "hr";

  private static final Pageable DEFAULT_PAGE_REQUEST = new PageRequest(0, 10);

  @Autowired
  private ApplicationsRepository applicationsRepository;

  @Autowired
  private StatesHistoryRepository statesHistoryRepository;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private StatesRepository statesRepository;

  @Test
  public void findTopByApplicationEntityOrderByCreationDateDescShouldNotFindStateForApplicationWithoutStates() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateEmail(CANDIDATE_EMAIL_D);

    // When
    StatesHistoryEntity
        statesHistoryEntity =
        this.statesHistoryRepository
            .findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertNull(statesHistoryEntity);
  }

  @Test
  public void findByApplicationEntityOrderByCreationDateDescShouldNotFindStateForApplicationWithoutStates() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateEmail(CANDIDATE_EMAIL_D);

    // When
    List<StatesHistoryEntity>
        statesHistoryEntityList =
        this.statesHistoryRepository
            .findByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertThat(statesHistoryEntityList, notNullValue());
    assertThat(statesHistoryEntityList, empty());
  }

  @Test
  public void findTopByApplicationEntityOrderByCreationDateDescShouldFindSingleStateForApplicationWithSingleState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateEmail(CANDIDATE_EMAIL_E);
    StatesHistoryEntity expectedStatesHistoryEntity = getExpectedStateEntityForSingleState();

    // When
    StatesHistoryEntity
        statesHistoryEntity =
        this.statesHistoryRepository
            .findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertStateEntity(statesHistoryEntity, expectedStatesHistoryEntity);
  }

  @Test
  public void findByApplicationEntityOrderByCreationDateDescShouldFindSingleStateForApplicationWithSingleState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateEmail(CANDIDATE_EMAIL_E);
    StatesHistoryEntity expectedStatesHistoryEntity = getExpectedStateEntityForSingleState();

    // When
    List<StatesHistoryEntity>
        statesHistoryEntityList =
        this.statesHistoryRepository
            .findByApplicationEntityOrderByCreationDateDesc(applicationEntity);
    assertThat(statesHistoryEntityList, notNullValue());
    assertThat(statesHistoryEntityList.size(), is(VALUE_ONE));
    StatesHistoryEntity statesHistoryEntity = statesHistoryEntityList.get(ZEROTH_ELEMENT);

    // Then
    assertStateEntity(statesHistoryEntity, expectedStatesHistoryEntity);
  }

  @Test
  public void findTopByApplicationEntityOrderByCreationDateDescShouldFindNewestStateForApplicationWithThreeState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateEmail(CANDIDATE_EMAIL_F);
    StatesHistoryEntity
        expectedStatesHistoryEntity =
        getExpectedSortingStateEntityListWithThreeElements().get(ZEROTH_ELEMENT);

    // When
    StatesHistoryEntity
        statesHistoryEntity =
        this.statesHistoryRepository
            .findTopByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertStateEntity(statesHistoryEntity, expectedStatesHistoryEntity);
  }

  @Test
  public void findByApplicationEntityOrderByCreationDateDescShouldFindThreeStateForApplicationWithThreeState() {
    // Given
    ApplicationEntity applicationEntity = getApplicationByCandidateEmail(CANDIDATE_EMAIL_F);

    // When
    List<StatesHistoryEntity>
        statesHistoryEntityList =
        this.statesHistoryRepository
            .findByApplicationEntityOrderByCreationDateDesc(applicationEntity);

    // Then
    assertThat(statesHistoryEntityList, notNullValue());
    assertThat(statesHistoryEntityList.size(), is(VALUE_THREE));
    checkSortingValidationWithThreeElements(statesHistoryEntityList);
  }

  private void checkSortingValidationWithThreeElements(
      List<StatesHistoryEntity> statesHistoryEntityList) {
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
            getExpectedApplicationEntity(CANDIDATE_EMAIL_E, FIRST_ID, CHANNEL_NAME_DIRECT, FIRST_ID,
                POSITION_NAME_DEVELOPER))
        .statesEntity(statesRepository.findByName(STATE_NAME_FIRST_TEST))
        .build();
  }

  private List<StatesHistoryEntity> getExpectedSortingStateEntityListWithThreeElements() {
    List<StatesHistoryEntity> statesHistoryEntityList = Arrays.asList(
        StatesHistoryEntity.builder()
            .applicationEntity(
                getExpectedApplicationEntity(CANDIDATE_EMAIL_F, FOURTH_ID, CHANNEL_NAME_FACEBOOK,
                    FIRST_ID, POSITION_NAME_DEVELOPER))
            .statesEntity(statesRepository.findByName(STATE_NAME_CODING))
            .build(),
        StatesHistoryEntity.builder()
            .applicationEntity(
                getExpectedApplicationEntity(CANDIDATE_EMAIL_F, FOURTH_ID, CHANNEL_NAME_FACEBOOK,
                    FIRST_ID, POSITION_NAME_DEVELOPER))
            .statesEntity(statesRepository.findByName(STATE_NAME_HR))
            .build(),
        StatesHistoryEntity.builder()
            .applicationEntity(
                getExpectedApplicationEntity(CANDIDATE_EMAIL_F, FOURTH_ID, CHANNEL_NAME_FACEBOOK,
                    FIRST_ID, POSITION_NAME_DEVELOPER))
            .statesEntity(statesRepository.findByName(STATE_NAME_FIRST_TEST))
            .build()
    );

    return statesHistoryEntityList;
  }

  private ApplicationEntity getApplicationByCandidateEmail(String candidateEmail) {
    CandidateEntity candidateEntity = this.candidateRepository.findByEmail(candidateEmail);

    Page<ApplicationEntity>
        pageResult =
        this.applicationsRepository.findByCandidateEntity(candidateEntity, DEFAULT_PAGE_REQUEST);

    return pageResult.getContent().get(ZEROTH_ELEMENT);
  }

  private ApplicationEntity getExpectedApplicationEntity(String candidateEmail, Long channelId,
                                                         String channelName, Long positionId,
                                                         String positionName) {
    CandidateEntity candidateEntity = this.candidateRepository.findByEmail(candidateEmail);

    ChannelEntity expectedChannelEntity = ChannelEntity.builder()
        .id(channelId)
        .name(channelName)
        .build();

    PositionEntity expectedPositionEntity = PositionEntity.builder()
        .id(positionId)
        .name(positionName)
        .build();

    Date currentDate = new Date();

    Page<ApplicationEntity>
        pageResult =
        this.applicationsRepository.findByCandidateEntity(candidateEntity, DEFAULT_PAGE_REQUEST);

    Long expectedApplicationId = pageResult.getContent().get(ZEROTH_ELEMENT).getId();
    ApplicationEntity expectedApplicationEntity = ApplicationEntity.builder()
        .id(expectedApplicationId)
        .positionEntity(expectedPositionEntity)
        .channelEntity(expectedChannelEntity)
        .candidateEntity(candidateEntity)
        .creationDate(currentDate)
        .build();

    return expectedApplicationEntity;
  }

  private void assertStateEntity(StatesHistoryEntity statesHistoryEntity,
                                 StatesHistoryEntity expectedStatesHistoryEntity) {
    assertThat(statesHistoryEntity, notNullValue());
    assertThat(expectedStatesHistoryEntity, notNullValue());

    assertThat(statesHistoryEntity.getStatesEntity(), notNullValue());
    assertThat(expectedStatesHistoryEntity.getStatesEntity(), notNullValue());
    assertThat(statesHistoryEntity.getStatesEntity(),
        is(expectedStatesHistoryEntity.getStatesEntity()));

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
}
