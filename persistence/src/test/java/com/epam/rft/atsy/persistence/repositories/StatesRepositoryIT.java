package com.epam.rft.atsy.persistence.repositories;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.epam.rft.atsy.persistence.entities.StatesEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StatesRepositoryIT extends AbstractRepositoryIT {

  private static final Long VALID_STATE_ID = 6L;
  private static final String VALID_STATE_NAME = "coding";

  private static final String INVALID_STATE_NAME = "notvalidname";

  @Autowired
  private StatesRepository statesRepository;

  @Test
  public void findByNameShouldNotFindStateWhenInvalidNameIsGiven() {
    //When
    StatesEntity result = statesRepository.findByName(INVALID_STATE_NAME);

    //Then
    assertThat(result, nullValue());
  }

  @Test
  public void findByNameShouldFindStateWhenValidNameIsGiven() {
    //Given
    StatesEntity
        expectedStatesEntity =
        StatesEntity.builder().id(VALID_STATE_ID).name(VALID_STATE_NAME).build();

    //When
    StatesEntity result = statesRepository.findByName(VALID_STATE_NAME);

    //Then
    assertThat(result, is(expectedStatesEntity));
  }

  @Test
  public void findByNameShouldNotFindStateWhenGivenNameIsNull() {
    //When
    StatesEntity result = statesRepository.findByName(null);

    //Then
    assertThat(result, is(nullValue()));
  }
}
