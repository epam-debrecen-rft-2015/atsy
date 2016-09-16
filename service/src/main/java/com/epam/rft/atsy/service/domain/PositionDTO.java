package com.epam.rft.atsy.service.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * DTO for PositionEntity
 * See {@link com.epam.rft.atsy.persistence.entities.PositionEntity}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PositionDTO extends LogicallyDeletableDTOS {

  @NotNull
  @Size(min = 1)
  private String name;

  @Builder
  public PositionDTO(Long id, Boolean deleted, String name) {
    super(id, deleted);
    this.name = name;
  }
}
