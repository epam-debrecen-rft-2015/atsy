package com.epam.rft.atsy.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents the state of the candidate during the application process, in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "states", schema = "atsy")
public class StatesEntity extends SuperEntity {

  @Column(name = "name")
  private String name;

  @Builder
  public StatesEntity(Long id, String name) {
    super(id);
    this.name = name;
  }
}
