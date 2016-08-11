package com.epam.rft.atsy.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "state_flow", schema = "atsy")

/**
 * Represents which state is available from the given state in the database.
 * e.g.: You can switch a candidate's state from "agree" state to "accept" state.
 */
public class StateFlowEntity extends SuperEntity {

  @OneToOne
  @JoinColumn(name = "from_id")
  private StatesEntity fromStateEntity;

  @OneToOne
  @JoinColumn(name = "to_id")
  private StatesEntity toStateEntity;

  @Builder
  public StateFlowEntity(Long id, StatesEntity fromStateEntity, StatesEntity toStateEntity) {
    super(id);
    this.fromStateEntity = fromStateEntity;
    this.toStateEntity = toStateEntity;
  }
}
