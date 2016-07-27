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
@Builder
@Entity
@Table(name = "state_flow", schema = "atsy")
public class StateFlowEntity extends SuperEntity {

  @OneToOne
  @JoinColumn(name = "from_id")
  private StatesEntity fromStateEntity;

  @OneToOne
  @JoinColumn(name = "to_id")
  private StatesEntity toStateEntity;
}
