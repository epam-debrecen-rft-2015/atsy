package com.epam.rft.atsy.persistence.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CandidateRepositoryIT extends AbstractRepositoryIT {

  private static final Long CANDIDATE_A_ID = 1L;
  private static final Long CANDIDATE_B_ID = 2L;
  private static final Long CANDIDATE_C_ID = 3L;

  private static final String CANDIDATE_A_NAME = "Candidate A";
  private static final String CANDIDATE_B_NAME = "Candidate B";
  private static final String CANDIDATE_C_NAME = "Candidate C";

  private static final String CANDIDATE_A_EMAIL = "candidate.a@atsy.com";
  private static final String CANDIDATE_B_EMAIL = "candidate.b@atsy.com";
  private static final String CANDIDATE_C_EMAIL = "candidate.c@atsy.com";

  private static final String CANDIDATE_A_PHONE = "+36105555555";
  private static final String CANDIDATE_B_PHONE = "+36106666666";
  private static final String CANDIDATE_C_PHONE = "+36107777777";

  private static final String CANDIDATE_A_DESCRIPTION = "Elegáns, kicsit furi";
  private static final String CANDIDATE_B_DESCRIPTION = "Össze-vissza beszélt";
  private static final String CANDIDATE_C_DESCRIPTION = StringUtils.EMPTY;

  private static final String CANDIDATE_A_REFERER = "google";
  private static final String CANDIDATE_B_REFERER = StringUtils.EMPTY;
  private static final String CANDIDATE_C_REFERER = "haverok";

  private static final Short CANDIDATE_A_LANGUAGE_SKILL = 5;
  private static final Short CANDIDATE_B_LANGUAGE_SKILL = 2;
  private static final Short CANDIDATE_C_LANGUAGE_SKILL = 8;

  public static final String CANDIDATE_C_POSITION = "Fejlesztő";

  private static final String EXISTING_CANDIDATE_EMAIL = CANDIDATE_A_EMAIL;

  private static final String NON_EXISTENT_CANDIDATE_EMAIL = "does@not.exist";

  private static final String NON_EXISTENT_CANDIDATE_NAME = "Candidate X";

  private static final CandidateEntity candidateA = CandidateEntity.builder()
      .id(CANDIDATE_A_ID)
      .name(CANDIDATE_A_NAME)
      .email(CANDIDATE_A_EMAIL)
      .phone(CANDIDATE_A_PHONE)
      .description(CANDIDATE_A_DESCRIPTION)
      .referer(CANDIDATE_A_REFERER)
      .languageSkill(CANDIDATE_A_LANGUAGE_SKILL).build();


  private static final CandidateEntity candidateB = CandidateEntity.builder()
      .id(CANDIDATE_B_ID)
      .name(CANDIDATE_B_NAME)
      .email(CANDIDATE_B_EMAIL)
      .phone(CANDIDATE_B_PHONE)
      .description(CANDIDATE_B_DESCRIPTION)
      .referer(CANDIDATE_B_REFERER)
      .languageSkill(CANDIDATE_B_LANGUAGE_SKILL)
      .build();

  private static final CandidateEntity candidateC = CandidateEntity.builder()
      .id(CANDIDATE_C_ID)
      .name(CANDIDATE_C_NAME)
      .email(CANDIDATE_C_EMAIL)
      .phone(CANDIDATE_C_PHONE)
      .description(CANDIDATE_C_DESCRIPTION)
      .referer(CANDIDATE_C_REFERER)
      .languageSkill(CANDIDATE_C_LANGUAGE_SKILL)
      .build();

  public static final int DEFAULT_PAGESIZE = 10;

  private static final PageRequest
      DEFAULT_PAGEREQUEST =
      new PageRequest(0, DEFAULT_PAGESIZE, Sort.Direction.ASC, "name");

  private static final PageRequest
      DESCENDING_NAME_PAGEREQUEST =
      new PageRequest(0, DEFAULT_PAGESIZE,
          Sort.Direction.DESC, "name");

  private static final PageRequest
      DESCENDING_EMAIL_PAGEREQUEST =
      new PageRequest(0, DEFAULT_PAGESIZE,
          Sort.Direction.DESC, "email");

  @Autowired
  private CandidateRepository candidateRepository;

  private void assertCandidateValue(List<CandidateEntity> candidates, String expectedName,
                                    String expectedEmail, String expectedPhone) {
    for (CandidateEntity entity : candidates) {

      assertThat(entity, notNullValue());

      if (!expectedName.isEmpty()) {
        assertThat(entity.getName(), notNullValue());
        assertThat(entity.getName(), containsString(expectedName));
      }

      if (!expectedEmail.isEmpty()) {
        assertThat(entity.getEmail(), notNullValue());
        assertThat(entity.getEmail(), containsString(expectedEmail));
      }

      if (!expectedPhone.isEmpty()) {
        assertThat(entity.getPhone(), notNullValue());
        assertThat(entity.getPhone(), containsString(expectedPhone));
      }
    }
  }

  private void assertCandidateList(List<CandidateEntity> expectedCandidates,
                                   List<CandidateEntity> actualCandidates) {
    assertThat(actualCandidates.size(), is(expectedCandidates.size()));
    for (Iterator expected = expectedCandidates.iterator(), actual = actualCandidates.iterator();
         expected.hasNext() && actual.hasNext(); ) {
      assertThat(actual.next(), is(expected.next()));
    }
  }

  @Test
  public void findByCandidateFilterRequestShouldFindAllCandidatesWithEmptyFilterRequest() {
    //Given
    String name = "";
    String email = "";
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

    //When
    List<CandidateEntity>
        actualCandidate =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //The
    assertCandidateList(expectedCandidates, actualCandidate);
  }

  @Test
  public void findByCandidateFieldRequestShouldFindCandidateCOnlyByPositionFilter() {
    //Given
    String name = "";
    String email = "";
    String phone = "";
    String position = "f";

    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //Then
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindCandidateAOnlyByPhoneFilter() {
    //Given
    String name = "";
    String email = "";
    String phone = CANDIDATE_A_PHONE;
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindCandidateBOnlyByNameFilter() {
    //Given
    String name = CANDIDATE_B_NAME;
    String email = "";
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateB);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindCandidateCOnlyByEmailFilter() {
    //Given
    String name = "";
    String email = CANDIDATE_C_EMAIL;
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindThreeCandidatesInDescendingOrderByEmail() {
    //Given
    String name = "";
    String email = "atsy.com";
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC, candidateB, candidateA);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position,
                DESCENDING_EMAIL_PAGEREQUEST).getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindThreeCandidatesInAscendingOrderByEmail() {
    //Given
    String name = "";
    String email = "atsy.com";
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position,
                DEFAULT_PAGEREQUEST).getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindThreeCandidatesByPhoneFilterInAscendingOrderByName() {
    //Given
    String name = "";
    String email = "";
    String phone = "+3610";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position,
                DEFAULT_PAGEREQUEST).getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindThreeCandidatesInDescendingOrderByName() {
    //Given
    String name = "";
    String email = "";
    String phone = "+3610";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC, candidateB, candidateA);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position,
                DESCENDING_NAME_PAGEREQUEST).getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindThreeCandidatesInDescendingOrderByPhoneFilteredByName() {
    //Given
    String name = "Candidate";
    String email = "";
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC, candidateB, candidateA);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position,
                DESCENDING_NAME_PAGEREQUEST).getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldFindThreeCandidatesInAscendingOrderByPhoneFilteredByName() {
    //Given
    String name = "Candidate";
    String email = "";
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position,
                DEFAULT_PAGEREQUEST).getContent();

    //Then
    assertCandidateValue(actualCandidates, name, email, phone);
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldNotFindNonExistentCandidate() {
    //Given
    String name = NON_EXISTENT_CANDIDATE_NAME;
    String email = "";
    String phone = "";
    String position = "";
    List<CandidateEntity> expectedCandidates = Collections.emptyList();

    //When
    List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //Then
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByEmailShouldNotFindNonexistentCandidate() {
    //When
    CandidateEntity candidate = this.candidateRepository.findByEmail(NON_EXISTENT_CANDIDATE_EMAIL);

    //Then
    assertThat(candidate, nullValue());
  }

  @Test
  public void findByEmailShouldFindExistingCandidate() {
    //When
    CandidateEntity candidate = this.candidateRepository.findByEmail(EXISTING_CANDIDATE_EMAIL);

    //Then
    assertThat(candidate, notNullValue());
    assertThat(candidate.getEmail(), is(EXISTING_CANDIDATE_EMAIL));
  }
}