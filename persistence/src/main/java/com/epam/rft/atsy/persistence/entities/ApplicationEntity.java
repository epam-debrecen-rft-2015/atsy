package com.epam.rft.atsy.persistence.entities;

import com.epam.rft.atsy.persistence.entities.states.StateEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "applications", schema = "atsy")
public class ApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long applicationId;
    @Column(name = "creation_date")
    private Date creationDate;
    @OneToOne
    @JoinColumn(name = "candidate_id")
    private CandidateEntity candidateEntity;

    public ApplicationEntity() {
    }

    public ApplicationEntity(Long applicationId, Date creationDate, CandidateEntity candidateEntity) {
        this.applicationId = applicationId;
        this.creationDate = creationDate;
        this.candidateEntity = candidateEntity;
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

    public CandidateEntity getCandidateEntity() {
        return candidateEntity;
    }

    public void setCandidateEntity(CandidateEntity candidateEntity) {
        this.candidateEntity = candidateEntity;
    }
}
