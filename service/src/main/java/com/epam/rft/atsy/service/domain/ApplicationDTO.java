package com.epam.rft.atsy.service.domain;

import com.epam.rft.atsy.service.domain.states.StateDTO;

import java.util.Date;

public class ApplicationDTO {

    private Long applicationId;
    private Date creationDate;
    private Long candidateId;

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long applicationId, Date creationDate, Long candidateId) {
        this.applicationId = applicationId;
        this.creationDate = creationDate;
        this.candidateId = candidateId;
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
}
