package com.epam.rft.atsy.web;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
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

  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "statehistory.error.parse.date")
  private String creationDate;

  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "statehistory.error.parse.date")
  private String feedbackDate;

  @Min(value = 0, message = "candidate.error.language.incorrect")
  @Max(value = 10, message = "candidate.error.language.incorrect")
  private Short languageSkill;

  private String description;

  private String result;

  @Min(value = 0, message = "statehistory.error.offeredMoney.negative")
  private Long offeredMoney;

  @Min(value = 0, message = "statehistory.error.claim.negative")
  private Long claim;

  private String dayOfStart;

  private String stateFullName;

  private Long stateId;

  private String stateName;
}
