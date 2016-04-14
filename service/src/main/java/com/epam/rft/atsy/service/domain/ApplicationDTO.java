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
}
