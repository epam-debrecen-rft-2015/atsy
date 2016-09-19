package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.entities.PositionEntity;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class PositionRepositoryIT extends AbstractRepositoryIT {

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

  public void findAllByDeletedFalseShouldReturnOnlyNonDeletedPositions() {
    // Given

    // When
    List<PositionEntity> actualPositionList = this.positionRepository.findAllByDeletedFalse();

    // Then
    assertThat(actualPositionList, notNullValue());
    assertFalse(actualPositionList.isEmpty());
    assertNonDeletedChannelEntityList(actualPositionList);
  }

  @Test

  public void findAllByDeletedFalseShouldNotContainAnEntityThatHasDeletedFieldWithTrueValue() {
    // Given

    PositionEntity
        positionEntityWithTrueDeletedField =
        this.positionRepository.findByName(POSITION_NAME_WITH_TRUE_DELETED_FIELD);

    // When
    List<PositionEntity> actualPositionList = this.positionRepository.findAllByDeletedFalse();

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