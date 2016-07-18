package com.epam.rft.atsy.service.domain.states.builder;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.AbstractStateDTO;
import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/6/2016.
 */


@AllArgsConstructor
public abstract class AbstractStateBuilder<B extends AbstractStateBuilder<B, T>, T extends AbstractStateDTO>
    implements Builder<T> {

    protected String name;
    protected T object;


    public B id(Long id) {
        object.setId(id);
        return (B) this;
    }

    public B candidateId(Long candidateId) {
        object.setCandidateId(candidateId);
        return (B) this;
    }

    public B position(PositionDTO position) {
        object.setPosition(position);
        return (B) this;
    }

    public B applicationDTO(ApplicationDTO applicationDTO) {
        object.setApplicationDTO(applicationDTO);
        return (B) this;
    }

    public B languageSkill(Short languageSkill) {
        object.setLanguageSkill(languageSkill);
        return (B) this;
    }

    public B description(String description) {
        object.setDescription(description);
        return (B) this;
    }

    public B result(String result) {
        object.setResult(result);
        return (B) this;
    }

    public B offeredMoney(Long offeredMoney) {
        object.setOfferedMoney(offeredMoney);
        return (B) this;
    }

    public B claim(Long claim) {
        object.setClaim(claim);
        return (B) this;
    }

    public B feedbackDate(Date feedbackDate) {
        object.setFeedbackDate(feedbackDate);
        return (B) this;
    }

    public B stateType(String stateType) {
        object.setStateType(stateType);
        return (B) this;
    }

    public B stateIndex(Integer stateIndex) {
        object.setStateIndex(stateIndex);
        return (B) this;
    }


    @Override public T build() {
        return object;
    }

}
