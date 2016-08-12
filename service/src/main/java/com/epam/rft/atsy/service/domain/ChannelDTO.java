package com.epam.rft.atsy.service.domain;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data transfer class that is used to transfer the data of a channel between the service and web
 * layers. See {@link com.epam.rft.atsy.persistence.entities.ChannelEntity ChannelEntity}.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO implements Serializable {

  private Long id;

  @NotNull
  @Size(min = 1)
  private String name;

}
