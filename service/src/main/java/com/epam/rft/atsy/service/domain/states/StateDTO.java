package com.epam.rft.atsy.service.domain.states;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class StateDTO {

  private Long id;
  private String name;
}
