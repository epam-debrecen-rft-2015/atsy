package com.epam.rft.atsy.service.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class provides the logically deletable behaviour for the extended classes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class LogicallyDeletableDTOS extends SuperDTO {

  @Getter(AccessLevel.NONE)
  private Boolean deleted = false;

  public Boolean isDeleted() {
    return deleted;
  }

  public LogicallyDeletableDTOS(Long id, Boolean deleted) {
    super(id);
    this.deleted = deleted;
  }
}
