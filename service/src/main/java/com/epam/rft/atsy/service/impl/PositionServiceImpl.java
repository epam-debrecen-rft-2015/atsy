package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.PositionDAO;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ikantik.
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Resource
    private PositionDAO positionDAO;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public Collection<PositionDTO> getAllPositions() {
        Collection<PositionEntity> positionEntities = positionDAO.loadAll();
        Type targetListType = new TypeToken<List<PositionEntity>>() {
        }.getType();
        return modelMapper.map(positionEntities, targetListType);
    }
}
