package com.epam.rft.atsy.persistence.entities.states;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by tothd on 2015. 12. 02..
 */
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
