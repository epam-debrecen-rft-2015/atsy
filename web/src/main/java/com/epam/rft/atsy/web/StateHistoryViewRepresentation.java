package com.epam.rft.atsy.web;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  private Date creationDate;

  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", message = "statehistory.error.parse.date")
  private String feedbackDate;

  @Min(value = 0, message = "candidate.error.language.incorrect")
  @Max(value = 10, message = "candidate.error.language.incorrect")
  private Short languageSkill;

  @Size(max = 2000, message = "statehistory.error.description.length")
  private String description;

  @Min(value = 0, message = "candidate.error.result.range")
  @Max(value = 100, message = "candidate.error.result.range")
  private Short result;

  @Min(value = 0, message = "statehistory.error.offeredMoney.negative")
  private Long offeredMoney;

  @Min(value = 0, message = "statehistory.error.claim.negative")
  private Long claim;

  private Date dayOfStart;

  private String stateFullName;

  private Long stateId;

  private String stateName;

  @Min(value = 0, message = "statehistory.error.recommendation.range")
  @Max(value = 1, message = "statehistory.error.recommendation.range")
  private Integer recommendation;

  @Size(max = 100, min = 3, message = "statehistory.error.reviewerName.length")
  private String reviewerName;

  @Min(value = 0, message = "statehistory.error.recommendedPositionLevel.range")
  @Max(value = 5, message = "statehistory.error.recommendedPositionLevel.range")
  private Short recommendedPositionLevel;
}
