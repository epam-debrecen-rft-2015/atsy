package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.PositionDAO;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
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
        Type targetListType = new TypeToken<List<PositionDTO>>() {
        }.getType();
        return modelMapper.map(positionEntities, targetListType);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void saveOrUpdate(PositionDTO position) {
        PositionEntity entity =modelMapper.map(position, PositionEntity.class);
        try {
            if (entity.getPositionId() == null) {
                positionDAO.create(entity);
            } else {
                positionDAO.update(entity);
            }
        }catch (ConstraintViolationException constraint){
            throw new DuplicateRecordException();
        }
    }
}
