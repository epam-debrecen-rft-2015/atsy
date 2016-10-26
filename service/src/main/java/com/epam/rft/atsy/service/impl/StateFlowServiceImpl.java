package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.StateFlowEntity;
import com.epam.rft.atsy.persistence.entities.StatesEntity;
import com.epam.rft.atsy.persistence.repositories.StateFlowRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.StateFlowService;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateFlowDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The StateFlowService that responsible for actions with StateFlows.
 */
@Service
public class StateFlowServiceImpl implements StateFlowService {

  @Autowired
  private ConverterService converterService;

  @Autowired
  private StateFlowRepository stateFlowRepository;

  /**
   * Returns the collection of StateFlowDTOs where the FromStateDTo is equals to the given
   * parameter
   * @param statesDTO the FromStateDTO
   * @return the collection of StateFlowDTOs
   */
  @Transactional(readOnly = true)
  @Override
  public Collection<StateFlowDTO> getStateFlowDTOByFromStateDTO(StateDTO statesDTO) {
    Assert.notNull(statesDTO);
    Assert.notNull(statesDTO.getId());
    Assert.notNull(statesDTO.getName());

    List<StateFlowEntity>
        stateFlowEntities =
        stateFlowRepository.findByFromStateEntity(converterService.convert(statesDTO,
            StatesEntity.class));

    return converterService.convert(stateFlowEntities, StateFlowDTO.class);
  }
  @Transactional(readOnly = true)
  @Override
  public boolean isAvailableFromLastState(List<StateHistoryDTO> representationList, String state) {
    Collection<StateFlowDTO> available = getStateFlowDTOByFromStateDTO(representationList.get(0).getStateDTO());
    List<StateFlowDTO> availableList= available.stream().filter(p->p.getToStateDTO().getName().equals(state)).collect(
       Collectors.toList());
    if(availableList.isEmpty()){
      return false;
    }
    return true;
  }

}
