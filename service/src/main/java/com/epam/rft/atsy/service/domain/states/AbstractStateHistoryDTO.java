package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Super class of all the SateHistory* classes. Contains a certain state of a candidate,
 * independently of the time. Also declares additional information about the candidate and his
 * application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractStateHistoryDTO implements Serializable {

  private Long id;
  private Long candidateId;
  private PositionDTO position;
  private ChannelDTO channel;
  private ApplicationDTO applicationDTO;
  private Short languageSkill;
  private String description;
  private Short result;
  private Long offeredMoney;
  private Long claim;
  private Date feedbackDate;
  private Date dayOfStart;
  private Date dateOfEnter;
  private StateDTO stateDTO;
  private Boolean recommendation;
  private String reviewerName;
  private Short recommendedPositionLevel;

}
