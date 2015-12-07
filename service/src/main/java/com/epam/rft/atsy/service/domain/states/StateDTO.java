package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

/**
 * Created by tothd on 2015. 12. 07..
 */
public abstract class StateDTO {

    private Long stateId;
    private Long candidateId;
    private PositionDTO position;
    private Date creationDate;
    private String description;
    private StateDTO nextState;


    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StateDTO getNextState() {
        return nextState;
    }

    public void setNextState(StateDTO nextState) {
        this.nextState = nextState;
    }
}
