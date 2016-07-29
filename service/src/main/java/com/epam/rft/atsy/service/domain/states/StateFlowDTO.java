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
public class StateFlowDTO {

  private StateDTO fromStateDTO;
  private StateDTO toStateDTO;
}
