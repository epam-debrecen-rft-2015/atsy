package com.epam.rft.atsy.persistence.dao.impl;

import com.epam.rft.atsy.persistence.dao.PositionDAO;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional(Transactional.TxType.REQUIRED)
@Repository
public class PositionDAOImpl extends GenericDAOImpl<PositionEntity, Long> implements PositionDAO {

}
