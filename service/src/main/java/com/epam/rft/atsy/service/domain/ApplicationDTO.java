package com.epam.rft.atsy.service.domain;


import java.util.Date;

public class ApplicationDTO {

    private Long id;
    private Date creationDate;
    private Long candidateId;
    private Long positionId;

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long id, Date creationDate, Long candidateId, Long positionId) {
        this.id = id;
        this.creationDate = creationDate;
        this.candidateId = candidateId;
        this.positionId = positionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        if (id != null ? !id.equals(that.id) : that.id != null)
            return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (candidateId != null ? !candidateId.equals(that.candidateId) : that.candidateId != null) return false;
        return !(positionId != null ? !positionId.equals(that.positionId) : that.positionId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (candidateId != null ? candidateId.hashCode() : 0);
        result = 31 * result + (positionId != null ? positionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", candidateId=" + candidateId +
                ", positionId=" + positionId +
                '}';
    }
}
