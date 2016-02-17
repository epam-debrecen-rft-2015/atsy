package com.epam.rft.atsy.service;

import com.epam.rft.atsy.service.domain.PositionDTO;

import java.util.Collection;

public interface PositionService {

    Collection<PositionDTO> getAllPositions();

    void saveOrUpdate(PositionDTO position);
}
