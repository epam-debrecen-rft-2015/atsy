package com.epam.rft.atsy.service.domain;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Data transfer class that is used to transfer the data of a channel between the service and web
 * layers. See {@link com.epam.rft.atsy.persistence.entities.ChannelEntity ChannelEntity}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ChannelDTO extends LogicallyDeletableDto implements Serializable {

  private Long id;

  @NotNull
  @Size(min = 1)
  private String name;

  @Builder
  public ChannelDTO(Long id, Boolean deleted, String name) {
    super(deleted);
    this.id = id;
    this.name = name;
  }
}
