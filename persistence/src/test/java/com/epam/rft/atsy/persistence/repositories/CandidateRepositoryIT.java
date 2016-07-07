package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class CandidateRepositoryIT extends AbstractRepositoryIT {

    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    public void findAllCandidatesByFilterRequestWithValidData() {
        for (int i = 1; i < 9; i++) {

            //Given
            String name = "", email = "", phone = "";

            if (i / 2 > 0) {
                name = "date";
            }

            if (i % 4 == 0) {
                email = "atsy.";
            }

            if (i % 8 == 0) {
                phone = "36";
            }

            //When
            List<CandidateEntity> result
                    = this.candidateRepository.findAllCandidatesByFilterRequest(name, email, phone, new Sort(Sort.DEFAULT_DIRECTION, "name"));

            System.err.println("i="+ i);
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
