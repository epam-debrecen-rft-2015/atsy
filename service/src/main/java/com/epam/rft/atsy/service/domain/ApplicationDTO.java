package com.epam.rft.atsy.service.domain;


import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data transfer class that is used to transfer every data of an application between the service and
 * web layers. See {@link com.epam.rft.atsy.persistence.entities.ApplicationEntity
 * ApplicationEntity}.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO implements Serializable {

  private Long id;
  private Date creationDate;
  private Long candidateId;
  private Long positionId;
  private Long channelId;

}
