package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

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

    private static final String EXISTING_CANDIDATE_NAME = CANDIDATE_A_NAME;

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



    @Autowired
    private CandidateRepository candidateRepository;

    private Sort createSort(Sort.Direction direction, String property) {
        return new Sort(direction, property);
    }

    private void assertCandidateValue(List<CandidateEntity> candidates, String expectedName, String expectedEmail, String expectedPhone) {
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

    private void assertCandidateList(List<CandidateEntity> expectedCandidates, List<CandidateEntity> actualCandidates) {
        assertThat(actualCandidates.size(), is(expectedCandidates.size()));
        for (Iterator expected = expectedCandidates.iterator(), actual = actualCandidates.iterator(); expected.hasNext() && actual.hasNext();) {
            assertThat(actual.next(), is(expected.next()));
        }
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindCandidateAOnlyByPhoneFilter() {
        //Given
        String name = "";
        String email = "";
        String phone = CANDIDATE_A_PHONE;
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, null);

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindCandidateBOnlyByNameFilter() {
        //Given
        String name = CANDIDATE_B_NAME;
        String email = "";
        String phone = "";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateB);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, null);

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindCandidateCOnlyByEmailFilter() {
        //Given
        String name = "";
        String email = CANDIDATE_C_EMAIL;
        String phone = "";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, null);

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindThreeCandidatesInDescendingOrderByEmail() {
        //Given
        String name = "";
        String email = "atsy.com";
        String phone = "";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC, candidateB, candidateA);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, createSort(Sort.Direction.DESC, "email"));

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindThreeCandidatesInAscendingOrderByEmail() {
        //Given
        String name = "";
        String email = "atsy.com";
        String phone = "";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, createSort(Sort.Direction.ASC, "email"));

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindThreeCandidatesInAscendingOrderByName() {
        //Given
        String name = "";
        String email = "";
        String phone = "+3610";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, createSort(Sort.Direction.ASC, "name"));

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindThreeCandidatesInDescendingOrderByName() {
        //Given
        String name = "";
        String email = "";
        String phone = "+3610";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC, candidateB, candidateA);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, createSort(Sort.Direction.DESC, "name"));

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindThreeCandidatesInDescendingOrderByPhoneFilteredByName() {
        //Given
        String name = "Candidate";
        String email = "";
        String phone = "";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateC, candidateB, candidateA);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, createSort(Sort.Direction.DESC, "phone"));

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldFindThreeCandidatesInAscendingOrderByPhoneFilteredByName() {
        //Given
        String name = "Candidate";
        String email = "";
        String phone = "";
        List<CandidateEntity> expectedCandidates = Arrays.asList(candidateA, candidateB, candidateC);

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, createSort(Sort.Direction.ASC, "phone"));

        //Then
        assertCandidateValue(actualCandidates, name, email, phone);
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findAllByNameContainingAndEmailContainingAndPhoneContainingShouldGiveEmptyList() {
        //Given
        String name = NON_EXISTENT_CANDIDATE_NAME;
        String email = "";
        String phone = "";
        List<CandidateEntity> expectedCandidates = Collections.emptyList();

        //When
        List<CandidateEntity> actualCandidates = candidateRepository.findAllByNameContainingAndEmailContainingAndPhoneContaining(name, email, phone, null);

        //Then
        assertCandidateList(expectedCandidates, actualCandidates);
    }

    @Test
    public void findByNameShouldNotFindNonexistentCandidate() {
        //When
        CandidateEntity candidate = this.candidateRepository.findByName(NON_EXISTENT_CANDIDATE_NAME);

        //Then
        assertThat(candidate, nullValue());
    }

    @Test
    public void findByNameShouldFindExistingCandidate() {
        //When
        CandidateEntity candidate = this.candidateRepository.findByName(EXISTING_CANDIDATE_NAME);

        //Then
        assertThat(candidate, notNullValue());
        assertThat(candidate.getName(), is(EXISTING_CANDIDATE_NAME));
    }
}
