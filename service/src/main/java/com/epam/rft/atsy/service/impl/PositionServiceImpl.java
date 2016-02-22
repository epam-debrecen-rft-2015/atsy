package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.PositionDAO;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Resource
    private PositionDAO positionDAO;
    @Resource
    private ModelMapper modelMapper;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public Collection<PositionDTO> getAllPositions() {
        Collection<PositionEntity> positionEntities = makeCollection(positionRepository.findAll());
        Type targetListType = new TypeToken<List<PositionDTO>>() {
        }.getType();
        return modelMapper.map(positionEntities, targetListType);
    }

    @Override
    public void saveOrUpdate(PositionDTO position) {
        Assert.notNull(position);
        PositionEntity entity = modelMapper.map(position, PositionEntity.class);
        try {
            if (entity.getPositionId() == null) {
                positionDAO.create(entity);
            } else {
                positionDAO.update(entity);
            }
        } catch (ConstraintViolationException | DataIntegrityViolationException constraint) {
            throw new DuplicateRecordException(position.getName());
        }
    }

    public <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }
}
