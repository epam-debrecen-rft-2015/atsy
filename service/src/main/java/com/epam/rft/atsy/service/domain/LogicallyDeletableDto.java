package com.epam.rft.atsy.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class provides the logically deletable behaviour for the extended classes.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class LogicallyDeletableDto {

  private Boolean deleted;
}
