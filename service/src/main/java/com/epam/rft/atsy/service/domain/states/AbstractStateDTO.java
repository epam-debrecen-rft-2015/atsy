package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

public abstract class AbstractStateDTO {

    private Long stateId;
    private Long candidateId;
    private PositionDTO position;
    private ApplicationDTO applicationDTO;
    private Short languageSkill;
    private String description;
    private String result;
    private Long offeredMoney;
    private Long claim;
    private Date feedbackDate;
    private String stateType;
    private Integer stateIndex;

    public AbstractStateDTO() {
    }

    public AbstractStateDTO(Long stateId, Long candidateId, PositionDTO position, ApplicationDTO applicationDTO, Short languageSkill, String description, String result, Long offeredMoney, Long claim, Date feedbackDate, String stateType, Integer stateIndex) {
        this.stateId = stateId;
        this.candidateId = candidateId;
        this.position = position;
        this.applicationDTO = applicationDTO;
        this.languageSkill = languageSkill;
        this.description = description;
        this.result = result;
        this.offeredMoney = offeredMoney;
        this.claim = claim;
        this.feedbackDate = feedbackDate;
        this.stateType = stateType;
        this.stateIndex = stateIndex;
    }

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

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public ApplicationDTO getApplicationDTO() {
        return applicationDTO;
    }

    public void setApplicationDTO(ApplicationDTO applicationDTO) {
        this.applicationDTO = applicationDTO;
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

        AbstractStateDTO that = (AbstractStateDTO) o;

        if (stateId != null ? !stateId.equals(that.stateId) : that.stateId != null) return false;
        if (candidateId != null ? !candidateId.equals(that.candidateId) : that.candidateId != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (applicationDTO != null ? !applicationDTO.equals(that.applicationDTO) : that.applicationDTO != null)
            return false;
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
        int result1 = stateId != null ? stateId.hashCode() : 0;
        result1 = 31 * result1 + (candidateId != null ? candidateId.hashCode() : 0);
        result1 = 31 * result1 + (position != null ? position.hashCode() : 0);
        result1 = 31 * result1 + (applicationDTO != null ? applicationDTO.hashCode() : 0);
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
        return "AbstractStateDTO{" +
                "stateId=" + stateId +
                ", candidateId=" + candidateId +
                ", position=" + position +
                ", applicationDTO=" + applicationDTO +
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
