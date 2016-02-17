package com.epam.rft.atsy.persistence.entities.states;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue(value = "wageOffer")
public class WageOfferStateEntity extends StateEntity{

    @Column(name = "offered_money")
    private Long offeredMoney;
    @Column(name = "claim")
    private Long claim;
    @Column(name = "feedback_date")
    private Date feedbackDate;

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
}
