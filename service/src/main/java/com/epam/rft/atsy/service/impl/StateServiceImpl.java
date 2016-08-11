package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.repositories.StatesRepository;
import com.epam.rft.atsy.service.StateService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class StateServiceImpl implements StateService {

  @Autowired
  private StatesRepository statesRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  @Override
  public StateDTO getStateDtoById(Long id) {
    Assert.notNull(id);
    return modelMapper.map(statesRepository.findOne(id), StateDTO.class);
  }

  @Transactional(readOnly = true)
  @Override
  public StateDTO getStateDtoByName(String name) {
    Assert.notNull(name);
    return modelMapper.map(statesRepository.findByName(name), StateDTO.class);
  }

}
