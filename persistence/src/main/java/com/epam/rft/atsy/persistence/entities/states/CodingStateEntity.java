package com.epam.rft.atsy.persistence.entities.states;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "coding")
public class CodingStateEntity extends StateEntity{
}
