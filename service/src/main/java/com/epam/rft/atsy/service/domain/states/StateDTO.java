package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

public class StateDTO extends AbstractStateDTO{

    private Date creationDate;

    public StateDTO() {
    }

    public StateDTO(Long stateId, Long candidateId, PositionDTO position, Long applicationId, Short languageSkill, String description, String result, Long offeredMoney, Long claim, Date feedbackDate, String stateType, Integer stateIndex, Date creationDate) {
        super(stateId, candidateId, position, applicationId, languageSkill, description, result, offeredMoney, claim, feedbackDate, stateType, stateIndex);
        this.creationDate = creationDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

}
