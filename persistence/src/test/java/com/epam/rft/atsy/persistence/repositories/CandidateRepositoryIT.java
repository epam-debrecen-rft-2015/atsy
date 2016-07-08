package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CandidateRepositoryIT extends AbstractRepositoryIT {

    private static final String VALID_NAME_SEARCH = "date A";
    private static final String VALID_EMAIL_SEARCH = "atsy.";
    private static final String VALID_PHONE_NUMBER_SEARCH = "77";

    private static final String INVALID_NAME_SEARCH = "date X";
    private static final String INVALID_EMAIL_SEARCH = "@dummydomain.com";
    private static final String INVALID_PHONE_NUMBER_SEARCH = "+3699";

    private static final List<CandidateEntity> POSITIVE_CANDIDATES_TEST =
            Arrays.asList(
                    CandidateEntity.builder().name("").email("").phone("").build(),
                    CandidateEntity.builder().name("").email("").phone(VALID_PHONE_NUMBER_SEARCH).build(),
                    CandidateEntity.builder().name("").email(VALID_EMAIL_SEARCH).phone("").build(),
                    CandidateEntity.builder().name(VALID_NAME_SEARCH).email("").phone("").build(),
                    CandidateEntity.builder().name("").email(VALID_EMAIL_SEARCH).phone(VALID_PHONE_NUMBER_SEARCH).build(),
                    CandidateEntity.builder().name(VALID_NAME_SEARCH).email("").phone(VALID_PHONE_NUMBER_SEARCH).build(),
                    CandidateEntity.builder().name(VALID_NAME_SEARCH).email(VALID_EMAIL_SEARCH).phone("").build(),
                    CandidateEntity.builder().name(VALID_NAME_SEARCH).email(VALID_EMAIL_SEARCH).phone(VALID_PHONE_NUMBER_SEARCH).build()

            );

    private static final List<CandidateEntity> NEGATIVE_CANDIDATES_TEST =
            Arrays.asList(
                    CandidateEntity.builder().name("").email("").phone(INVALID_PHONE_NUMBER_SEARCH).build(),
                    CandidateEntity.builder().name("").email(INVALID_EMAIL_SEARCH).phone("").build(),
                    CandidateEntity.builder().name(INVALID_NAME_SEARCH).email("").phone("").build(),
                    CandidateEntity.builder().name("").email(INVALID_EMAIL_SEARCH).phone(INVALID_PHONE_NUMBER_SEARCH).build(),
                    CandidateEntity.builder().name(INVALID_NAME_SEARCH).email("").phone(INVALID_PHONE_NUMBER_SEARCH).build(),
                    CandidateEntity.builder().name(INVALID_NAME_SEARCH).email(INVALID_EMAIL_SEARCH).phone("").build(),
                    CandidateEntity.builder().name(INVALID_NAME_SEARCH).email(INVALID_EMAIL_SEARCH).phone(INVALID_PHONE_NUMBER_SEARCH).build()

            );

    private Sort.Order createSortOrder(Sort.Direction direction, String property) {
        return new Sort.Order(direction, property);
    }

    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void findAllCandidatesByFilterRequestWithValidDataSortedByNameInAscendingOrder() {
        for (CandidateEntity candidate : POSITIVE_CANDIDATES_TEST) {
            //Given
            final String name = candidate.getName();
            final String email = candidate.getEmail();
            final String phone = candidate.getPhone();

            //When
            List<CandidateEntity> result = this.candidateRepository.findAllCandidatesByFilterRequest(
                    name,
                    email,
                    phone,
                    new Sort(createSortOrder(Sort.Direction.ASC, "name")));

            //Then
            assertCandidateEntityListWithSorting(result, name, email, phone, createSortOrder(Sort.Direction.ASC, "name"));
        }
    }

    private void assertCandidateEntityListWithSorting(List<CandidateEntity> candidates,
                                                      String expectedName,
                                                      String expectedEmail,
                                                      String expectedPhone,
                                                      Sort.Order sorting) {

        assertThat(candidates, notNullValue());

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

        boolean invariant = true;
        if (!candidates.isEmpty()) {
            CandidateEntity prev = candidates.get(0);
            if (sorting.getProperty().equals("name")) {
                if (sorting.isAscending()) {
                    for (CandidateEntity ent : candidates) {
                        if (prev.getName().compareTo(ent.getName()) >= 0) {
                            invariant = false;
                            break;
                        } else {
                            prev = ent;
                        }
                    }
                } else {
                    for (CandidateEntity ent : candidates) {
                        if (prev.getName().compareTo(ent.getName()) <= 0) {
                            invariant = false;
                            break;
                        } else {
                            prev = ent;
                        }
                    }
                }
            } else if (sorting.getProperty().equals("email")) {
                if (sorting.isAscending()) {
                    for (CandidateEntity ent : candidates) {
                        if (prev.getEmail().compareTo(ent.getEmail()) >= 0) {
                            invariant = false;
                            break;
                        } else {
                            prev = ent;
                        }
                    }
                } else {
                    for (CandidateEntity ent : candidates) {
                        if (prev.getEmail().compareTo(ent.getEmail()) <= 0) {
                            invariant = false;
                            break;
                        } else {
                            prev = ent;
                        }
                    }
                }
            } else if (sorting.getProperty().equals("phone")) {
                if (sorting.isAscending()) {
                    for (CandidateEntity ent : candidates) {
                        if (prev.getPhone().compareTo(ent.getPhone()) >= 0) {
                            invariant = false;
                            break;
                        } else {
                            prev = ent;
                        }
                    }
                } else {
                    for (CandidateEntity ent : candidates) {
                        if (prev.getEmail().compareTo(ent.getEmail()) <= 0) {
                            invariant = false;
                            break;
                        } else {
                            prev = ent;
                        }
                    }
                }
            }
        }

        assertTrue(invariant);
    }
}
