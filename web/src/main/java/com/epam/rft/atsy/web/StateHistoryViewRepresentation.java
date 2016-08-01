package com.epam.rft.atsy.web;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

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
  private String feedbackDate;
  private String dayOfStart;
  private String stateFullName;
  private Long stateId;
  private String stateName;
}
