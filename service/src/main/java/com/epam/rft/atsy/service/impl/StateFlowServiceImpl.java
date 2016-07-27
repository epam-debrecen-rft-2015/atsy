package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.repositories.StateFlowRepository;
import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Resource;

@Service
public class StateFlowServiceImpl implements StateFlowService {

  @Resource
  private ModelMapper modelMapper;

  @Autowired
  private StateFlowRepository stateFlowRepository;

  @Override
  public Collection<StateFlowDTO> getStateFlowDTOByFromStateDTO(StateDTO statesDTO) {
    Assert.notNull(statesDTO);
    Assert.notNull(statesDTO.getId());
    Assert.notNull(statesDTO.getName());

    List<StateFlowEntity>
        stateFlowEntities =
        stateFlowRepository.findByFromStateEntity(modelMapper.map(statesDTO,
            StatesEntity.class));

    List<StateFlowDTO> stateFlowDTOs = new ArrayList<>();
    for (StateFlowEntity stateFlowEntity : stateFlowEntities) {
      StateFlowDTO stateFlowDTO = new StateFlowDTO();
      stateFlowDTO
          .setFromStateDTO(modelMapper.map(stateFlowEntity.getFromStateEntity(), StateDTO.class));
      stateFlowDTO
          .setToStateDTO(modelMapper.map(stateFlowEntity.getToStateEntity(), StateDTO.class));
      stateFlowDTOs.add(stateFlowDTO);
    }
    return stateFlowDTOs;
  }
}
