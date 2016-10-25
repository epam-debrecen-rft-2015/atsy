package com.epam.rft.atsy.service.domain;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class represents the current state of a candidate's application. Also stores a little
 * information about the process of the application, like it's previous state and it's last
 * modification time.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateApplicationDTO {

  private Long lastStateId;
  private Long id;
  private String name;
  private Date creationDate;
  private Date modificationDate;
  private String stateType;

}
