package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Collection;

/**
 * Created by Ikantik.
 */
public interface PositionService {

    public Collection<PositionDTO> getAllPositions();
}
