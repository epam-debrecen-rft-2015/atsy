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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationEntity that = (ApplicationEntity) o;

        if (applicationId != null ? !applicationId.equals(that.applicationId) : that.applicationId != null)
            return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (candidateEntity != null ? !candidateEntity.equals(that.candidateEntity) : that.candidateEntity != null)
            return false;
        return !(positionEntity != null ? !positionEntity.equals(that.positionEntity) : that.positionEntity != null);

    }

    @Override
    public int hashCode() {
        int result = applicationId != null ? applicationId.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (candidateEntity != null ? candidateEntity.hashCode() : 0);
        result = 31 * result + (positionEntity != null ? positionEntity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationEntity{" +
                "applicationId=" + applicationId +
                ", creationDate=" + creationDate +
                ", candidateEntity=" + candidateEntity +
                ", positionEntity=" + positionEntity +
                '}';
    }
}
