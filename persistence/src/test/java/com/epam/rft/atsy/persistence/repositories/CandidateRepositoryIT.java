package com.epam.rft.atsy.persistence.repositories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
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

  private static final String EXISTING_CANDIDATE_EMAIL = CANDIDATE_A_EMAIL;

  private static final String NON_EXISTENT_CANDIDATE_EMAIL = "does@not.exist";
  private static final String NON_EXISTENT_CANDIDATE_NAME = "Candidate X";

  private static final String CANDIDATE_NAME_FRAGMENT = "Candidate";
  private static final String CANDIDATE_EMAIL_FRAGMENT = "atsy.com";
  private static final String CANDIDATE_C_POSITION_FRAGMENT = "f";
  private static final String CANDIDATE_PHONE_FRAGMENT = "+3610";

  private static final int DEFAULT_PAGESIZE = 10;

  private static final String SORT_NAME_CANDIDATE_NAME = "candidate.name";
  private static final String SORT_NAME_CANDIDATE_EMAIL = "candidate.email";

  private static final CandidateEntity candidateA = CandidateEntity.builder()
      .id(CANDIDATE_A_ID)
      .deleted(false)
      .name(CANDIDATE_A_NAME)
      .email(CANDIDATE_A_EMAIL)
      .phone(CANDIDATE_A_PHONE)
      .description(CANDIDATE_A_DESCRIPTION)
      .referer(CANDIDATE_A_REFERER)
      .languageSkill(CANDIDATE_A_LANGUAGE_SKILL).build();
  private static final CandidateEntity candidateB = CandidateEntity.builder()
      .id(CANDIDATE_B_ID)
      .deleted(false)
      .name(CANDIDATE_B_NAME)
      .email(CANDIDATE_B_EMAIL)
      .phone(CANDIDATE_B_PHONE)
      .description(CANDIDATE_B_DESCRIPTION)
      .referer(CANDIDATE_B_REFERER)
      .languageSkill(CANDIDATE_B_LANGUAGE_SKILL)
      .build();
  private static final CandidateEntity candidateC = CandidateEntity.builder()
      .id(CANDIDATE_C_ID)
      .deleted(false)
      .name(CANDIDATE_C_NAME)
      .email(CANDIDATE_C_EMAIL)
      .phone(CANDIDATE_C_PHONE)
      .description(CANDIDATE_C_DESCRIPTION)
      .referer(CANDIDATE_C_REFERER)
      .languageSkill(CANDIDATE_C_LANGUAGE_SKILL)
      .build();
  private static final PageRequest
      DEFAULT_PAGEREQUEST =
      new PageRequest(0, DEFAULT_PAGESIZE, Sort.Direction.ASC, SORT_NAME_CANDIDATE_NAME);
  private static final PageRequest
      DESCENDING_NAME_PAGEREQUEST =
      new PageRequest(0, DEFAULT_PAGESIZE,
          Sort.Direction.DESC, SORT_NAME_CANDIDATE_NAME);
  private static final PageRequest
      DESCENDING_EMAIL_PAGEREQUEST =
      new PageRequest(0, DEFAULT_PAGESIZE,
          Sort.Direction.DESC, SORT_NAME_CANDIDATE_EMAIL);

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
    final String name = StringUtils.EMPTY;
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

    //When
    final List<CandidateEntity>
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
    final String name = StringUtils.EMPTY;
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String position = CANDIDATE_C_POSITION_FRAGMENT;

    final List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC);

    //When
    final List<CandidateEntity>
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
    final String name = StringUtils.EMPTY;
    final String email = StringUtils.EMPTY;
    final String phone = CANDIDATE_A_PHONE;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA);

    //When
    final List<CandidateEntity>
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
    final String name = CANDIDATE_B_NAME;
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity> expectedCandidates = Arrays.asList(candidateB);

    //When
    final List<CandidateEntity>
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
    final String name = StringUtils.EMPTY;
    final String email = CANDIDATE_C_EMAIL;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC);

    //When
    final List<CandidateEntity>
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
    final String name = StringUtils.EMPTY;
    final String email = CANDIDATE_EMAIL_FRAGMENT;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity>
        expectedCandidates =
        Arrays.asList(candidateC, candidateB, candidateA);

    //When
    final List<CandidateEntity>
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
    final String name = StringUtils.EMPTY;
    final String email = CANDIDATE_EMAIL_FRAGMENT;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity>
        expectedCandidates =
        Arrays.asList(candidateA, candidateB, candidateC);

    //When
    final List<CandidateEntity>
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
    final String name = StringUtils.EMPTY;
    final String email = StringUtils.EMPTY;
    final String phone = CANDIDATE_PHONE_FRAGMENT;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity>
        expectedCandidates =
        Arrays.asList(candidateA, candidateB, candidateC);

    //When
    final List<CandidateEntity>
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
    final String name = StringUtils.EMPTY;
    final String email = StringUtils.EMPTY;
    final String phone = CANDIDATE_PHONE_FRAGMENT;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity>
        expectedCandidates =
        Arrays.asList(candidateC, candidateB, candidateA);

    //When
    final List<CandidateEntity>
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
    final String name = CANDIDATE_NAME_FRAGMENT;
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity>
        expectedCandidates =
        Arrays.asList(candidateC, candidateB, candidateA);

    //When
    final List<CandidateEntity>
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
    final String name = CANDIDATE_NAME_FRAGMENT;
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity>
        expectedCandidates =
        Arrays.asList(candidateA, candidateB, candidateC);

    //When
    final List<CandidateEntity>
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
    final String name = NON_EXISTENT_CANDIDATE_NAME;
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;
    final List<CandidateEntity> expectedCandidates = Collections.emptyList();

    //When
    final List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //Then
    assertCandidateList(expectedCandidates, actualCandidates);
  }

  @Test
  public void findByCandidateFilterRequestShouldNotFindCandidateThatHasDeletedFieldWithTrueValue() {
    //Given
    final String name = StringUtils.EMPTY;
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String position = StringUtils.EMPTY;

    //When
    final List<CandidateEntity>
        actualCandidates =
        candidateRepository
            .findByCandidateFilterRequest(name, email, phone, position, DEFAULT_PAGEREQUEST)
            .getContent();

    //Then
    MatcherAssert.assertThat(actualCandidates, notNullValue());
    MatcherAssert.assertThat(actualCandidates.isEmpty(), is(false));
    assertNonDeletedCandidateList(actualCandidates);
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

  private void assertNonDeletedCandidateList(List<CandidateEntity> candidateEntityList) {
    if (candidateEntityList.stream().anyMatch(x -> x.isDeleted() == true)) {
      Assert.fail();
    }
  }
}