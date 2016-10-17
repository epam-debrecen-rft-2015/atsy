package com.epam.rft.atsy.service.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Data transfer class that is used to transfer every data of an application between the service and
 * web layers. See {@link com.epam.rft.atsy.persistence.entities.ApplicationEntity
 * ApplicationEntity}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ApplicationDTO extends LogicallyDeletableDTO {

  private Date creationDate;
  private Long candidateId;
  private Long positionId;
  private Long channelId;

  @Builder
  public ApplicationDTO(Long id, Boolean deleted, Date creationDate,
                        Long candidateId, Long positionId, Long channelId) {
    super(id, deleted);
    this.creationDate = creationDate;
    this.candidateId = candidateId;
    this.positionId = positionId;
    this.channelId = channelId;
  }
}
