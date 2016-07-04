package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDTO extends AbstractStateDTO{

    private Date creationDate;
    private ChannelDTO channel;


    public StateDTO(Long id, Long candidateId, PositionDTO position, ApplicationDTO applicationDTO, Short languageSkill, String description, String result, Long offeredMoney, Long claim, Date feedbackDate, String stateType, Integer stateIndex, Date creationDate, ChannelDTO channel) {
        super(id, candidateId, position, applicationDTO, languageSkill, description, result, offeredMoney, claim, feedbackDate, stateType, stateIndex);
        this.creationDate = creationDate;
        this.channel = channel;
    }

}
