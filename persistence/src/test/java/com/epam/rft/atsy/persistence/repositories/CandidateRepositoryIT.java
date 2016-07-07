package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class CandidateRepositoryIT extends AbstractRepositoryIT {

    private static String possibleName = "date A";
    private static String possibleEmail = "atsy.";
    private static String possiblePhone = "77";

    private static List<CandidateEntity> positiveTestCases =
            Arrays.asList(
                    CandidateEntity.builder().name("").email("").phone("").build(),
                    CandidateEntity.builder().name("").email("").phone(possiblePhone).build(),
                    CandidateEntity.builder().name("").email(possibleEmail).phone("").build(),
                    CandidateEntity.builder().name(possibleName).email("").phone("").build(),
                    CandidateEntity.builder().name("").email(possibleEmail).phone(possiblePhone).build(),
                    CandidateEntity.builder().name(possibleName).email("").phone(possiblePhone).build(),
                    CandidateEntity.builder().name(possibleName).email(possibleEmail).phone("").build(),
                    CandidateEntity.builder().name(possibleName).email(possibleEmail).phone(possiblePhone).build()

            );

    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void findAllCandidatesByFilterRequestWithValidData() {
        for (CandidateEntity candidate : positiveTestCases) {
            //Given
            String name = candidate.getName();
            String email = candidate.getEmail();
            String phone = candidate.getPhone();

            //When
            List<CandidateEntity> result
                    = this.candidateRepository.findAllCandidatesByFilterRequest(name, email, phone, new Sort(Sort.DEFAULT_DIRECTION, "name"));

            System.err.println("name:" + name);
            System.err.println("email:" + email);
            System.err.println("phone:" + phone);
            System.err.println(result);
            //Then
            assertCandidateEntityList(result, name, email, phone);
        }
    }

    private void assertCandidateEntityList(List<CandidateEntity> actual,
                                           String expectedName,
                                           String expectedEmail,
                                           String expectedPhone) {

        for (CandidateEntity entity : actual) {

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
}
