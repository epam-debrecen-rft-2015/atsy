package com.epam.rft.atsy.service.domain;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateApplicationDTO {

  private Long lastStateId;
  private Long applicationId;
  private String positionName;
  private Date creationDate;
  private Date modificationDate;
  private String stateType;

}
