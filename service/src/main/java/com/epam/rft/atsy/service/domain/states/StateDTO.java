package com.epam.rft.atsy.service.domain.states;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the state of the candidate during the application process.
 * See: {@link com.epam.rft.atsy.persistence.entities.StatesEntity}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class StateDTO {

  private Long id;
  private String name;
}
