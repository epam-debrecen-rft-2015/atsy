package com.epam.rft.atsy.persistence.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "States", schema = "atsy")
public class StateEntity extends SuperEntity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StateEntity that = (StateEntity) o;

        if (applicationEntity != null ? !applicationEntity.equals(that.applicationEntity) : that.applicationEntity != null)
            return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (languageSkill != null ? !languageSkill.equals(that.languageSkill) : that.languageSkill != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        if (offeredMoney != null ? !offeredMoney.equals(that.offeredMoney) : that.offeredMoney != null) return false;
        if (claim != null ? !claim.equals(that.claim) : that.claim != null) return false;
        if (feedbackDate != null ? !feedbackDate.equals(that.feedbackDate) : that.feedbackDate != null) return false;
        if (stateType != null ? !stateType.equals(that.stateType) : that.stateType != null) return false;
        return !(stateIndex != null ? !stateIndex.equals(that.stateIndex) : that.stateIndex != null);

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + (applicationEntity != null ? applicationEntity.hashCode() : 0);
        result1 = 31 * result1 + (creationDate != null ? creationDate.hashCode() : 0);
        result1 = 31 * result1 + (languageSkill != null ? languageSkill.hashCode() : 0);
        result1 = 31 * result1 + (description != null ? description.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (offeredMoney != null ? offeredMoney.hashCode() : 0);
        result1 = 31 * result1 + (claim != null ? claim.hashCode() : 0);
        result1 = 31 * result1 + (feedbackDate != null ? feedbackDate.hashCode() : 0);
        result1 = 31 * result1 + (stateType != null ? stateType.hashCode() : 0);
        result1 = 31 * result1 + (stateIndex != null ? stateIndex.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "StateEntity{" +
                "applicationEntity=" + applicationEntity +
                ", creationDate=" + creationDate +
                ", languageSkill=" + languageSkill +
                ", description='" + description + '\'' +
                ", result='" + result + '\'' +
                ", offeredMoney=" + offeredMoney +
                ", claim=" + claim +
                ", feedbackDate=" + feedbackDate +
                ", stateType='" + stateType + '\'' +
                ", stateIndex=" + stateIndex +
                '}';
    }
}

