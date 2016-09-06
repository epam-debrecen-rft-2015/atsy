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

/**
 * Represents information about the different states of an application, in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "states_history", schema = "atsy")
public class StatesHistoryEntity extends SuperEntity {

  @OneToOne
  @JoinColumn(name = "application_id")
  private ApplicationEntity applicationEntity;

  @Column(name = "creation_date")
  private Date creationDate;

  @Column(name = "description")
  private String description;

  @Column(name = "first_test_result")
  private Short result;

  @Column(name = "offered_money")
  private Long offeredMoney;

  @Column(name = "claim")
  private Long claim;

  @Column(name = "feedback_date")
  private Date feedbackDate;

  @Column(name = "day_of_start")
  private Date dayOfStart;

  @Column(name = "date_of_enter")
  private Date dateOfEnter;

  @Column(name = "recommendation")
  private Boolean recommendation;

  @Column(name = "reviewer_name")
  private String reviewerName;

  @Column(name = "recommended_position_level")
  private Short recommendedPositionLevel;

  @OneToOne
  @JoinColumn(name = "state_id")
  private StatesEntity statesEntity;

  @OneToOne
  @JoinColumn(name = "channel_id")
  private ChannelEntity channelEntity;

  @OneToOne
  @JoinColumn(name = "position_id")
  private PositionEntity positionEntity;

  @Builder
  public StatesHistoryEntity(Long id, ApplicationEntity applicationEntity, Date creationDate,
                             String description, Short result,
                             Long offeredMoney,
                             Long claim, Date feedbackDate, Date dayOfStart,
                             Date dateOfEnter,
                             Boolean recommendation,
                             String reviewerName,
                             Short recommendedPositionLevel,
                             StatesEntity statesEntity, ChannelEntity channelEntity,
                             PositionEntity positionEntity) {
    super(id);
    this.applicationEntity = applicationEntity;
    this.creationDate = creationDate;
    this.description = description;
    this.result = result;
    this.offeredMoney = offeredMoney;
    this.claim = claim;
    this.feedbackDate = feedbackDate;
    this.dayOfStart = dayOfStart;
    this.dateOfEnter = dateOfEnter;
    this.recommendation = recommendation;
    this.reviewerName = reviewerName;
    this.recommendedPositionLevel = recommendedPositionLevel;
    this.statesEntity = statesEntity;
    this.channelEntity = channelEntity;
    this.positionEntity = positionEntity;
  }
}

