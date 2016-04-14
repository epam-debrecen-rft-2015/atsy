package com.epam.rft.atsy.persistence.entities;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "States", schema = "atsy")
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long stateId;
    @Column(name = "candidate_id")
    private Long candidateId;
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = true)
    private PositionEntity positionId;
    @OneToOne
    @JoinColumn(name = "application_id")
    private ApplicationEntity applicationEntity;
    @Column(name = "creation_date")
    private Date creationDate;
    @Column(name = "language_skill")
    private Short languageSkill;
    @Column(name = "description")
    private String description;
    @Column(name = "first_test_result")
    private String result;
    @Column(name = "offered_money")
    private Long offeredMoney;
    @Column(name = "claim")
    private Long claim;
    @Column(name = "feedback_date")
    private Date feedbackDate;
    @Column(name = "state_type", updatable = false)
    private String stateType;
    @Column(name = "state_index")
    private Integer stateIndex;

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

    public PositionEntity getPositionId() {
        return positionId;
    }

    public void setPositionId(PositionEntity positionId) {
        this.positionId = positionId;
    }

    public ApplicationEntity getApplicationEntity() {
        return applicationEntity;
    }

    public void setApplicationEntity(ApplicationEntity applicationEntity) {
        this.applicationEntity = applicationEntity;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Short getLanguageSkill() {
        return languageSkill;
    }

    public void setLanguageSkill(Short languageSkill) {
        this.languageSkill = languageSkill;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public Integer getStateIndex() {
        return stateIndex;
    }

    public void setStateIndex(Integer stateIndex) {
        this.stateIndex = stateIndex;
    }
}

