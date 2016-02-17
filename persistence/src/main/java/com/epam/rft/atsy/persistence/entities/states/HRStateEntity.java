package com.epam.rft.atsy.persistence.entities.states;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "hr")
public class HRStateEntity extends StateEntity{

    @Column(name = "language_skill")
    private Short languageSkill;

    public Short getLanguageSkill() {
        return languageSkill;
    }

    public void setLanguageSkill(Short languageSkill) {
        this.languageSkill = languageSkill;
    }
}
