package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

public class StateViewDTO extends AbstractStateDTO{

    private String creationDate;

    public StateViewDTO() {
    }

    public StateViewDTO(Long stateId, Long candidateId, PositionDTO position, Long applicationId, Short languageSkill, String description, String result, Long offeredMoney, Long claim, Date feedbackDate, String stateType, Integer stateIndex, String creationDate) {
        super(stateId, candidateId, position, applicationId, languageSkill, description, result, offeredMoney, claim, feedbackDate, stateType, stateIndex);
        this.creationDate = creationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
