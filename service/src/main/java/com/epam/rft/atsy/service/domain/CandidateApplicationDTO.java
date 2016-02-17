package com.epam.rft.atsy.service.domain;

public class CandidateApplicationDTO {

    private Long lastStateId;
    private String positionName;
    private String creationDate;
    private String modificationDate;
    private String stateType;

    public Long getLastStateId() {
        return lastStateId;
    }

    public void setLastStateId(Long lastStateId) {
        this.lastStateId = lastStateId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }
}
