package com.epam.rft.atsy.service.domain;


import java.util.Date;

public class ApplicationDTO {

    private Long applicationId;
    private Date creationDate;
    private Long candidateId;
    private Long positionId;

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long applicationId, Date creationDate, Long candidateId, Long positionId) {
        this.applicationId = applicationId;
        this.creationDate = creationDate;
        this.candidateId = candidateId;
        this.positionId = positionId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationDTO that = (ApplicationDTO) o;

        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (candidateId != null ? !candidateId.equals(that.candidateId) : that.candidateId != null) return false;
        return !(positionId != null ? !positionId.equals(that.positionId) : that.positionId != null);

    }

    @Override
    public int hashCode() {
        int result = applicationId != null ? applicationId.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (candidateId != null ? candidateId.hashCode() : 0);
        result = 31 * result + (positionId != null ? positionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
                "applicationId=" + applicationId +
                ", creationDate=" + creationDate +
                ", candidateId=" + candidateId +
                ", positionId=" + positionId +
                '}';
    }
}
