package com.epam.rft.atsy.service.domain;

public class CandidateApplicationDTO {

    private Long lastStateId;
    private Long applicationId;
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

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateApplicationDTO that = (CandidateApplicationDTO) o;

        if (lastStateId != null ? !lastStateId.equals(that.lastStateId) : that.lastStateId != null) return false;
        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (positionName != null ? !positionName.equals(that.positionName) : that.positionName != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (modificationDate != null ? !modificationDate.equals(that.modificationDate) : that.modificationDate != null)
            return false;
        return !(stateType != null ? !stateType.equals(that.stateType) : that.stateType != null);

    }

    @Override
    public int hashCode() {
        int result = lastStateId != null ? lastStateId.hashCode() : 0;
        result = 31 * result + (applicationId != null ? applicationId.hashCode() : 0);
        result = 31 * result + (positionName != null ? positionName.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        result = 31 * result + (stateType != null ? stateType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CandidateApplicationDTO{" +
                "lastStateId=" + lastStateId +
                ", applicationId=" + applicationId +
                ", positionName='" + positionName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", modificationDate='" + modificationDate + '\'' +
                ", stateType='" + stateType + '\'' +
                '}';
    }
}
