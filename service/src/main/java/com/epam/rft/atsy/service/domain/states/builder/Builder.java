package com.epam.rft.atsy.service.domain.states.builder;

import com.epam.rft.atsy.service.domain.states.AbstractStateDTO;

/**
 * Created by Gabor_Ivanyi-Nagy on 7/6/2016.
 */
public interface Builder<T extends AbstractStateDTO> {

    T build();
}
