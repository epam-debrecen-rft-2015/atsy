package com.epam.rft.atsy.persistence.repositories;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PositionRepositoryIT extends AbstractRepositoryIT {

  private static final Long POSITION_ID_DEVELOPER = 1L;
  private static final String POSITION_NAME_DEVELOPER = "Fejlesztő";
  private static final String POSITION_NAME_NON_EXISTENT = "Asztalitenisz oktató";

  @Autowired
  private PositionRepository positionRepository;

  @Test
  public void findByNameShouldReturnNullWhenPositionNameIsNull() {
    // Given

    // When
    PositionEntity actualPositionEntity = positionRepository.findByName(null);

    // Then
    assertThat(actualPositionEntity, nullValue());
  }

  @Test
  public void findByNameShouldReturnNullWhenPositionNameIsNonExistent() {
    // Given

    // When
    PositionEntity actualPositionEntity = positionRepository.findByName(POSITION_NAME_NON_EXISTENT);

    // Then
    assertThat(actualPositionEntity, nullValue());
  }

  @Test
  public void findByNameShouldReturnExistingPositionEntityWhenPositionNameIsExisting() {
    // Given
    PositionEntity expectedPositionEntity =
        PositionEntity.builder().id(POSITION_ID_DEVELOPER).name(POSITION_NAME_DEVELOPER).build();

    // When
    PositionEntity actualPositionEntity = positionRepository.findByName(POSITION_NAME_DEVELOPER);

    // Then
    assertThat(actualPositionEntity, notNullValue());
    assertThat(actualPositionEntity, equalTo(expectedPositionEntity));
  }
}
