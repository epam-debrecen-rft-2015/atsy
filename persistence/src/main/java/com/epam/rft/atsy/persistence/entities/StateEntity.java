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
@Table(name = "states_history", schema = "atsy")
public class StateEntity extends SuperEntity {


  @OneToOne
  @JoinColumn(name = "application_id")
  private ApplicationEntity applicationEntity;

  @Column(name = "creation_date")
  private Date creationDate;

  @Column(name = "language_skill")
  private Short languageSkill;

  @Column(name = "description")
  private String description;

  @Column(name = "first_test_result")
  private String result;

  @Column(name = "offered_money")
  private Long offeredMoney;

  @Column(name = "claim")
  private Long claim;

  @Column(name = "feedback_date")
  private Date feedbackDate;

  @Column(name = "day_of_start")
  private Date dayOfStart;

  @Column(name = "state_type", updatable = false)
  private String stateType;

  @Column(name = "state_index")
  private Integer stateIndex;


  @Builder
  public StateEntity(Long id, ApplicationEntity applicationEntity, Date creationDate,
                     Short languageSkill, String description, String result, Long offeredMoney,
                     Long claim, Date feedbackDate, Date dayOfStart, String stateType, Integer stateIndex) {
    super(id);
    this.applicationEntity = applicationEntity;
    this.creationDate = creationDate;
    this.languageSkill = languageSkill;
    this.description = description;
    this.result = result;
    this.offeredMoney = offeredMoney;
    this.claim = claim;
    this.feedbackDate = feedbackDate;
    this.dayOfStart = dayOfStart;
    this.stateType = stateType;
    this.stateIndex = stateIndex;
  }
}

