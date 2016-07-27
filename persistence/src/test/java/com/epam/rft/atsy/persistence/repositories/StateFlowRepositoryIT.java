package com.epam.rft.atsy.persistence.repositories;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

@Sql("classpath:sql/stateflow/stateflow.sql")
public class StateFlowRepositoryIT extends AbstractRepositoryIT {

  private static final Long INVALID_STATE_ID = -1L;
  private static final Long VALID_STATE_ID = 80L;
  private static final String VALID_FROM_STATE_NAME = "test";
  public static final long STATES_ENTITY_ACCEPT_ID = 12L;
  public static final String STATES_ENTITY_ACCEPT_NAME = "accept";
  public static final long STATES_ENTITY_REJECT_ID = 13L;
  public static final String STATES_ENTITY_REJECT_NAME = "reject";
  public static final long STATES_ENTITY_PAUSE_ID = 14L;
  public static final String STATES_ENTITY_PAUSE_NAME = "pause";

  private static final StatesEntity
      FROM_STATES_ENTITY =
      StatesEntity.builder().id(VALID_STATE_ID).name(VALID_FROM_STATE_NAME).build();
  private static final StatesEntity
      TO_STATES_ENTITY_ACCEPT =
      StatesEntity.builder().id(STATES_ENTITY_ACCEPT_ID).name(STATES_ENTITY_ACCEPT_NAME).build();
  private static final StatesEntity
      TO_STATES_ENTITY_REJECT =
      StatesEntity.builder().id(STATES_ENTITY_REJECT_ID).name(STATES_ENTITY_REJECT_NAME).build();
  private static final StatesEntity
      TO_STATES_ENTITY_PAUSE =
      StatesEntity.builder().id(STATES_ENTITY_PAUSE_ID).name(STATES_ENTITY_PAUSE_NAME).build();

  private static final StateFlowEntity
      STATE_FLOW_ENTITY_ONE =
      StateFlowEntity.builder().id(55L).fromStateEntity(FROM_STATES_ENTITY)
          .toStateEntity(TO_STATES_ENTITY_ACCEPT).build();
  private static final StateFlowEntity
      STATE_FLOW_ENTITY_TWO =
      StateFlowEntity.builder().id(56L).fromStateEntity(FROM_STATES_ENTITY)
          .toStateEntity(TO_STATES_ENTITY_REJECT).build();
  private static final StateFlowEntity
      STATE_FLOW_ENTITY_THREE =
      StateFlowEntity.builder().id(57L).fromStateEntity(FROM_STATES_ENTITY)
          .toStateEntity(TO_STATES_ENTITY_PAUSE).build();
  @Autowired
  private StateFlowRepository stateFlowRepository;
  @Autowired
  private StatesRepository statesRepository;

  @Test
  public void findByFromStateEntityShouldNotFindStateFlowEntityListWhenInvalidFromStateEntityIsGiven() {
    //Given
    StatesEntity statesEntity = this.statesRepository.findOne(INVALID_STATE_ID);

    //When
    List<StateFlowEntity> result = this.stateFlowRepository.findByFromStateEntity(statesEntity);

    //Then
    assertThat(result, is(emptyCollectionOf(StateFlowEntity.class)));
  }

  @Test
  public void findByFromStateEntityShouldNotFindStateFlowEntityListWhenGivenFromStateEntityIsNull() {

    //When
    List<StateFlowEntity> result = this.stateFlowRepository.findByFromStateEntity(null);

    //Then
    assertThat(result, is(emptyCollectionOf(StateFlowEntity.class)));
  }

  @Test
  public void findByFromStateEntityShouldFindStateFlowEntityListWhenValidFromStateEntityIsGiven() {
    List<StateFlowEntity> expectedList = new ArrayList<>();
    expectedList.add(STATE_FLOW_ENTITY_ONE);
    expectedList.add(STATE_FLOW_ENTITY_TWO);
    expectedList.add(STATE_FLOW_ENTITY_THREE);
    //Given
    StatesEntity statesEntity = this.statesRepository.findOne(VALID_STATE_ID);

    //When
    List<StateFlowEntity> result = this.stateFlowRepository.findByFromStateEntity(statesEntity);

    //Then
    assertThat(result,is(expectedList));
  }
}
