package com.epam.rft.atsy.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the available positions in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "positions", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class PositionEntity extends LogicallyDeletableEntity {

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Builder
  public PositionEntity(Long id, Boolean deleted, String name) {
    super(id, deleted);
    this.name = name;
  }

}
