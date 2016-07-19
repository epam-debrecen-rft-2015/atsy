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


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "channels", schema = "atsy", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class ChannelEntity extends SuperEntity implements java.io.Serializable {


  @Column(name = "name", nullable = false, length = 255, unique = true)
  private String name;


  @Builder
  public ChannelEntity(Long id, String name) {
    super(id);
    this.name = name;
  }


}
