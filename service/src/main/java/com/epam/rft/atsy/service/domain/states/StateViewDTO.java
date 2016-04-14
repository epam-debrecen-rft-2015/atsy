package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

public class StateViewDTO extends AbstractStateDTO{

    private String creationDate;

    public StateViewDTO() {
    }

    public StateViewDTO(Long stateId, Long candidateId, PositionDTO position, ApplicationDTO applicationDTO, Short languageSkill, String description, String result, Long offeredMoney, Long claim, Date feedbackDate, String stateType, Integer stateIndex) {
        super(stateId, candidateId, position, applicationDTO, languageSkill, description, result, offeredMoney, claim, feedbackDate, stateType, stateIndex);
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
