package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Date;

/**
 * Created by tothd on 2015. 12. 07..
 */
public abstract class StateDTO {

    private Long stateId;
    private Long candidateId;
    private PositionDTO position;
    private Date creationDate;
    private String description;
    private StateDTO nextState;


}
