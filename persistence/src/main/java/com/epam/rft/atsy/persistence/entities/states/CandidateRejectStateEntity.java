package com.epam.rft.atsy.persistence.entities.states;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by tothd on 2015. 12. 02..
 */
@Entity
@DiscriminatorValue(value = "candidateReject")
public class CandidateRejectStateEntity extends StateEntity{
}