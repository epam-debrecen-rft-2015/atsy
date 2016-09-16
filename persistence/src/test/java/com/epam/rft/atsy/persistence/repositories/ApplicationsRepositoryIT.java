package com.epam.rft.atsy.persistence.repositories;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Sql("classpath:sql/application/application.sql")
public class ApplicationsRepositoryIT extends AbstractRepositoryIT {

  public static final long CANDIDATE_A_ID = 1L;
  public static final long CANDIDATE_B_ID = 2L;
  public static final long CANDIDATE_C_ID = 3L;

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
        .deleted(false)
        .build();
    PositionEntity expectedPositionEntity = PositionEntity.builder()
        .id(1L)
        .name("Fejlesztő")
        .deleted(false)
        .build();
    Date nearNow = currentDateMinus(5);

    // When
    List<ApplicationEntity> result = this.repository.findByCandidateEntity(candidateEntityA);

    // Then
    assertThat(result, notNullValue());
    assertThat(result.size(), is(1));

    assertApplicationEntity(result.get(0), candidateEntityA, expectedChannelEntity,
        expectedPositionEntity, nearNow);
  }

  @Test
  public void findByCandidateEntityShouldFindThreeApplicationForCandidateWithThreeApplication()
      throws ParseException {
    // Given
    CandidateEntity candidateEntityC = this.candidateRepository.findOne(CANDIDATE_C_ID);
    ChannelEntity expectedChannelEntity = ChannelEntity.builder()
        .id(2L)
        .name("profession hírdetés")
        .deleted(false)
        .build();
    ChannelEntity expectedSecondChannelEntity = ChannelEntity.builder()
        .id(3L)
        .name("profession adatbázis")
        .deleted(false)
        .build();
    ChannelEntity expectedThirdChannelEntity = ChannelEntity.builder()
        .id(4L)
        .name("facebook")
        .deleted(false)
        .build();
    PositionEntity expectedPositionEntity = PositionEntity.builder()
        .id(1L)
        .name("Fejlesztő")
        .deleted(false)
        .build();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date expectedDate = simpleDateFormat.parse("2016-07-26 11:48:55");
    // When
    List<ApplicationEntity> result = this.repository.findByCandidateEntity(candidateEntityC);

    // Then
    assertThat(result, notNullValue());
    assertThat(result.size(), is(3));

    assertApplicationEntity(result.get(0), candidateEntityC, expectedChannelEntity,
        expectedPositionEntity, expectedDate);
    assertApplicationEntity(result.get(1), candidateEntityC, expectedSecondChannelEntity,
        expectedPositionEntity, expectedDate);
    assertApplicationEntity(result.get(2), candidateEntityC, expectedThirdChannelEntity,
        expectedPositionEntity, expectedDate);
  }

  private void assertApplicationEntity(ApplicationEntity application,
                                       CandidateEntity expectedCandidateEntity,
                                       ChannelEntity expectedChannelEntity,
                                       PositionEntity expectedPositionEntity, Date threshold) {
    assertThat(application, notNullValue());

    assertThat(application.getCandidateEntity(), notNullValue());
    assertThat(application.getCandidateEntity(), is(expectedCandidateEntity));

    assertThat(application.getChannelEntity(), notNullValue());
    assertThat(application.getChannelEntity(), is(expectedChannelEntity));

    assertThat(application.getPositionEntity(), notNullValue());
    assertThat(application.getPositionEntity(), is(expectedPositionEntity));

    assertThat(application.getCreationDate(), notNullValue());
    assertThat(application.getCreationDate(), greaterThan(threshold));
  }

  private Date currentDateMinus(long seconds) {
    return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
  }

}
