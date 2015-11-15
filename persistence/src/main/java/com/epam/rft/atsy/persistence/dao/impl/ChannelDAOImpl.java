package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.ChannelDAO;
import com.epam.rft.atsy.persistence.dao.PositionDAO;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by mates on 10/22/2015.
 */
@Transactional(Transactional.TxType.REQUIRED)
@Repository
public class ChannelDAOImpl extends GenericDAOImpl<ChannelEntity, Long> implements ChannelDAO {

}
