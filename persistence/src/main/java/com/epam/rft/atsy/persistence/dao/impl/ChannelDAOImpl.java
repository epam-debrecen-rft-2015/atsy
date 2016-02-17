package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.ChannelDAO;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional(Transactional.TxType.REQUIRED)
@Repository
public class ChannelDAOImpl extends GenericDAOImpl<ChannelEntity, Long> implements ChannelDAO {

}
