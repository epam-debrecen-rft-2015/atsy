package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractStateDTO {

  private Long id;
  private Long candidateId;
  private PositionDTO position;
  private ChannelDTO channel;
  private ApplicationDTO applicationDTO;
  private Short languageSkill;
  private String description;
  private String result;
  private Long offeredMoney;
  private Long claim;
  private Date feedbackDate;
  private String stateType;
  private Integer stateIndex;

}
