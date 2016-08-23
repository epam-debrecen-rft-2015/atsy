package com.epam.rft.atsy.persistence.repositories;


import com.epam.rft.atsy.persistence.entities.PositionEntity;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PositionRepositoryIT extends AbstractRepositoryIT {

  private static final String POSITION_NAME_EXISTING = "Fejlesztő";
  private static final String POSITION_NAME_NON_EXISTENT = "Table tennis instructor";
  private static final String POSITION_NAME_EXISTING_UPPER_CASE = POSITION_NAME_EXISTING.toUpperCase();

  @Autowired
  private PositionRepository positionRepository;


  @Test
  public void findByNameShouldReturnNullPositionWhenPositionNameNotExisting() {
    // Given

    // When
    PositionEntity positionEntity = positionRepository.findByName(POSITION_NAME_NON_EXISTENT);

    // Then
    assertThat(positionEntity, nullValue());
  }

  @Test
  public void findByNameShouldReturnExistingPositionWhenUpperCasePositionNameExisting() {
    // Given

    // When
    PositionEntity positionEntity = positionRepository.findByName(POSITION_NAME_EXISTING_UPPER_CASE);

    // Then
    assertThat(positionEntity, notNullValue());
    assertThat(positionEntity.getName(), equalTo(POSITION_NAME_EXISTING));
  }

  @Test
  public void findByNameShouldReturnExistingPositionWhenPositionNameExisting() {
    // Given

    // When
    PositionEntity positionEntity = positionRepository.findByName(POSITION_NAME_EXISTING);

    // Then
    assertThat(positionEntity, notNullValue());
    assertThat(positionEntity.getName(), equalTo(POSITION_NAME_EXISTING));
  }
}
