package com.epam.rft.atsy.service.domain.states;

import java.util.Date;

/**
 * Created by tothd on 2015. 12. 02..
 */
public class WageOfferStateDTO extends StateDTO {

    private Long offeredMoney;
    private Long claim;
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
