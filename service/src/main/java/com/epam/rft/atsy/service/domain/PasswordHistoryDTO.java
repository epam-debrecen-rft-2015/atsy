package com.epam.rft.atsy.service.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@ToString(exclude = "password")
@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO for PasswordHistoryEntity
 * See {@link com.epam.rft.atsy.persistence.entities.PasswordHistoryEntity}.
 */
public class PasswordHistoryDTO implements Serializable {


  private Long id;
  private Long userId;

  private String password;
  private Date changeDate;

}
