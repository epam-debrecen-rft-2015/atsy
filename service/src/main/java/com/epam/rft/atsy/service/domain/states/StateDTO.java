package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

public class StateDTO {

    private Long stateId;
    private Long candidateId;
    private PositionDTO position;
    private Date creationDate;
    private String description;
    private StateDTO nextState;
    private String stateType;
    private String result;
    private Short languageSkill;
    private Long offeredMoney;
    private Long claim;
    private Date feedbackDate;

    public StateDTO() {
    }

    public StateDTO(Long stateId, Long candidateId, PositionDTO position, Date creationDate, String description, StateDTO nextState, String stateType, String result, Short languageSkill, Long offeredMoney, Long claim, Date feedbackDate) {
        this.stateId = stateId;
        this.candidateId = candidateId;
        this.position = position;
        this.creationDate = creationDate;
        this.description = description;
        this.nextState = nextState;
        this.stateType = stateType;
        this.result = result;
        this.languageSkill = languageSkill;
        this.offeredMoney = offeredMoney;
        this.claim = claim;
        this.feedbackDate = feedbackDate;
    }

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

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Short getLanguageSkill() {
        return languageSkill;
    }

    public void setLanguageSkill(Short languageSkill) {
        this.languageSkill = languageSkill;
    }

    public Long getOfferedMoney() {
        return offeredMoney;
    }

    public void setOfferedMoney(Long offeredMoney) {
        this.offeredMoney = offeredMoney;
    }

    public Long getClaim() {
        return claim;
    }

    public void setClaim(Long claim) {
        this.claim = claim;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
}
