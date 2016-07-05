package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StateViewDTO extends AbstractStateDTO{


    private String creationDate;


    @Builder
    public StateViewDTO(Long id, Long candidateId, PositionDTO position, ApplicationDTO applicationDTO, Short languageSkill, String description, String result, Long offeredMoney, Long claim, Date feedbackDate, String stateType, Integer stateIndex, String creationDate) {
        super(id, candidateId, position, applicationDTO, languageSkill, description, result, offeredMoney, claim, feedbackDate, stateType, stateIndex);
        this.creationDate = creationDate;
    }


}
