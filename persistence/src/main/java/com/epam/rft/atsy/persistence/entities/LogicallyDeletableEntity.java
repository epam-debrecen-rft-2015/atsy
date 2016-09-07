package com.epam.rft.atsy.persistence.entities;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class provides the logically deletable behaviour for the extended classes.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public abstract class LogicallyDeletableEntity extends SuperEntity {

  @Column(name = "deleted")
  private Boolean deleted;

  @PrePersist
  void preInsert() {
    if (deleted == null) {
      deleted = false;
    }
  }

  public LogicallyDeletableEntity(Long id, Boolean deleted) {
    super(id);
    this.deleted = deleted;
  }
}
