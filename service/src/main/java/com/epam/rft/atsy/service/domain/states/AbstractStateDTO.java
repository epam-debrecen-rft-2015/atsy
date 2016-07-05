package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractStateDTO {

    private Long id;
    private Long candidateId;
    private PositionDTO position;
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
