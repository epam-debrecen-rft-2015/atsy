package com.epam.rft.atsy.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This class provides the logically deletable behaviour for the extended classes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class LogicallyDeletableDto {

  @Getter(AccessLevel.NONE)
  private Boolean deleted;

  public Boolean isDeleted() {
    return deleted;
  }
}
