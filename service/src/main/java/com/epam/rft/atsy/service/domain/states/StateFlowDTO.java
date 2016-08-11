package com.epam.rft.atsy.service.domain.states;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
/**
 * DTO for StateFlowEntity
 * See {@link com.epam.rft.atsy.persistence.entities.StateFlowEntity}.
 */
public class StateFlowDTO {

  private StateDTO fromStateDTO;
  private StateDTO toStateDTO;
}
