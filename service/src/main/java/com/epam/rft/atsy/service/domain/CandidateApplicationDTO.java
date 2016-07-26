package com.epam.rft.atsy.service.domain;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateApplicationDTO implements Serializable {

  private Long lastStateId;
  private Long applicationId;
  private String positionName;
  private String creationDate;
  private String modificationDate;
  private String stateType;

}
