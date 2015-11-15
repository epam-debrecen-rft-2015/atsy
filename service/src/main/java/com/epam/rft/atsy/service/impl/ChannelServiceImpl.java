package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.dao.ChannelDAO;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ikantik.
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Resource
    private ChannelDAO ChannelDAO;
    @Resource
    private ModelMapper modelMapper;

    @Override
    public Collection<ChannelDTO> getAllChannels() {
        Collection<ChannelEntity> ChannelEntities = ChannelDAO.loadAll();
        Type targetListType = new TypeToken<List<ChannelDTO>>() {
        }.getType();
        return modelMapper.map(ChannelEntities, targetListType);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public void saveOrUpdate(ChannelDTO channel) {
        Assert.notNull(channel);
        ChannelEntity entity = modelMapper.map(channel, ChannelEntity.class);
        try {
            if (entity.getChannelId() == null) {
                ChannelDAO.create(entity);
            } else {
                ChannelDAO.update(entity);
            }
        } catch (ConstraintViolationException | DataIntegrityViolationException constraint) {
            throw new DuplicateRecordException(channel.getName());
        }
    }
}
