package com.epam.rft.atsy.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "applications", schema = "atsy")
public class ApplicationEntity extends SuperEntity {


  @Column(name = "creation_date")
  private Date creationDate;

  @OneToOne
  @JoinColumn(name = "candidate_id")
  private CandidateEntity candidateEntity;

  @OneToOne
  @JoinColumn(name = "position_id")
  private PositionEntity positionEntity;

  @OneToOne
  @JoinColumn(name = "channel_id")
  private ChannelEntity channelEntity;


  @Builder
  public ApplicationEntity(Long id, Date creationDate, CandidateEntity candidateEntity,
                           PositionEntity positionEntity, ChannelEntity channelEntity) {
    super(id);
    this.creationDate = creationDate;
    this.candidateEntity = candidateEntity;
    this.positionEntity = positionEntity;
    this.channelEntity = channelEntity;
  }

}
