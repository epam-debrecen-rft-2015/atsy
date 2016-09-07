package com.epam.rft.atsy.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * This entity class represents how the candidate was found, how he got in touch with the company.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "channels", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQueries(@NamedQuery(name = "ChannelEntity.findAll", query = "SELECT channel FROM ChannelEntity channel WHERE channel.deleted is false"))
public class ChannelEntity extends LogicallyDeletableEntity implements java.io.Serializable {

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Builder
  public ChannelEntity(Long id, Boolean deleted, String name) {
    super(id, deleted);
    this.name = name;
  }
}
