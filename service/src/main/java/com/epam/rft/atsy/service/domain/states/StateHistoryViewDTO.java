package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.states.builder.AbstractStateBuilder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StateHistoryViewDTO extends AbstractStateHistoryDTO {


  private String creationDate;


  public static StateViewDTOBuilder builder() {
    return new StateViewDTOBuilder();
  }

  public StateViewDTOBuilder construct() {
    return new StateViewDTOBuilder(this);
  }


  public static class StateViewDTOBuilder
      extends AbstractStateBuilder<StateViewDTOBuilder, StateHistoryViewDTO> {

    private StateViewDTOBuilder(StateHistoryViewDTO stateHistoryViewDTO) {
      super("StateViewDTOBuilder", stateHistoryViewDTO);
    }

    private StateViewDTOBuilder() {
      this(new StateHistoryViewDTO());
    }

    public StateViewDTOBuilder creationDate(String creationDate) {
      object.setCreationDate(creationDate);
      return this;
    }

  }

}
