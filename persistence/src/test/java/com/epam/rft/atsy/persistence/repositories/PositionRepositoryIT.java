package com.epam.rft.atsy.persistence.repositories;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PositionRepositoryIT extends AbstractRepositoryIT {

  private static final String POSITION_NAME_EXISTING = "Fejlesztő";
  private static final String POSITION_NAME_NON_EXISTENT = "Asztalitenisz oktató";

  @Autowired
  private PositionRepository positionRepository;


  @Test
  public void findByNameShouldReturnNullPositionEntityWhenPositionNameIsNonExistent() {
    // Given

    // When
    PositionEntity positionEntity = positionRepository.findByName(POSITION_NAME_NON_EXISTENT);

    // Then
    assertThat(positionEntity, nullValue());
  }

  @Test
  public void findByNameShouldReturnExistingPositionEntityWhenPositionNameIsExisting() {
    // Given

    // When
    PositionEntity positionEntity = positionRepository.findByName(POSITION_NAME_EXISTING);

    // Then
    assertThat(positionEntity, notNullValue());
    assertThat(positionEntity.getName(), equalTo(POSITION_NAME_EXISTING));
  }
}
