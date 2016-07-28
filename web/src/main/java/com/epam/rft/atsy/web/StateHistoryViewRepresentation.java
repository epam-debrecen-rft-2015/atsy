package com.epam.rft.atsy.web;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateHistoryViewRepresentation {
  private Long id;
  private Long candidateId;
  private PositionDTO position;
  private ChannelDTO channel;
  private ApplicationDTO applicationDTO;
  private String creationDate;
  private Short languageSkill;
  private String description;
  private String result;
  private Long offeredMoney;
  private Long claim;
  private Date feedbackDate;
  private Date dayOfStart;
  private String stateFullName;
  private Long stateId;
  private String stateName;
}
