package com.epam.rft.atsy.persistence.entities.states;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "firstTest")
public class FirstTestStateEntity extends StateEntity{

    @Column(name = "first_test_result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
