package com.epam.rft.atsy.persistence.repositories;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PositionRepositoryIT extends AbstractRepositoryIT {

  private static final Long POSITION_ID_DEVELOPER = 1L;
  private static final String POSITION_NAME_DEVELOPER = "Fejlesztő";
  private static final String POSITION_NAME_NON_EXISTENT = "Asztalitenisz oktató";
  private static final String POSITION_NAME_WITH_TRUE_DELETED_FIELD = "Table tennis instructor";

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
    PositionEntity
        expectedPositionEntity =
        this.positionRepository.findByName(POSITION_NAME_DEVELOPER);

    // When
    PositionEntity actualPositionEntity = positionRepository.findByName(POSITION_NAME_DEVELOPER);

    // Then
    assertThat(actualPositionEntity, notNullValue());
    assertThat(actualPositionEntity, equalTo(expectedPositionEntity));
  }

  @Test
  public void findAllNonDeletedPositionEntityShouldReturnOnlyNonDeletedPositions() {
    // Given

    // When
    List<PositionEntity>
        actualPositionList =
        this.positionRepository.findAllNonDeletedPositionEntity();

    // Then
    assertThat(actualPositionList, notNullValue());
    assertFalse(actualPositionList.isEmpty());
    assertNonDeletedChannelEntityList(actualPositionList);
  }

  @Test
  public void findAllNonDeletedPositionEntityShouldNotContainAnEntityThatHasDeletedFieldWithTrueValue() {
    // Given
    PositionEntity
        positionEntityWithTrueDeletedField =
        this.positionRepository.findByName(POSITION_NAME_WITH_TRUE_DELETED_FIELD);

    // When
    List<PositionEntity>
        actualPositionList =
        this.positionRepository.findAllNonDeletedPositionEntity();

    // Then
    assertThat(actualPositionList, notNullValue());
    assertFalse(actualPositionList.isEmpty());
    assertFalse(actualPositionList.contains(positionEntityWithTrueDeletedField));
  }

  private void assertNonDeletedChannelEntityList(List<PositionEntity> positionEntityList) {
    if (positionEntityList.stream().anyMatch(c -> c.isDeleted())) {
      Assert.fail();
    }
  }
}