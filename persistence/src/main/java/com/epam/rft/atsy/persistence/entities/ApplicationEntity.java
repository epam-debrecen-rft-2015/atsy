package com.epam.rft.atsy.persistence.entities;

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
    @OneToOne
    @JoinColumn(name = "position_id")
    private PositionEntity positionEntity;

    public ApplicationEntity() {
    }

    public ApplicationEntity(Long applicationId, Date creationDate, CandidateEntity candidateEntity, PositionEntity positionEntity) {
        this.applicationId = applicationId;
        this.creationDate = creationDate;
        this.candidateEntity = candidateEntity;
        this.positionEntity = positionEntity;
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

    public PositionEntity getPositionEntity() {
        return positionEntity;
    }

    public void setPositionEntity(PositionEntity positionEntity) {
        this.positionEntity = positionEntity;
    }
}
