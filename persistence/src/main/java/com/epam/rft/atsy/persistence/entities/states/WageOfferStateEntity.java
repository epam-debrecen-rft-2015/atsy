package com.epam.rft.atsy.persistence.entities.states;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by tothd on 2015. 12. 02..
 */
@Entity
@DiscriminatorValue(value = "wageOffer")
public class WageOfferStateEntity extends StateEntity{

    @Column(name = "offered_money")
    private Long offeredMoney;
    @Column(name = "claim")
    private Long claim;
    @Column(name = "feedback_date")
    private Date feedbackDate;
}
