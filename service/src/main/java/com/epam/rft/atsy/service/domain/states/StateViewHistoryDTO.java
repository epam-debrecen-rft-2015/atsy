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
public class StateViewHistoryDTO extends AbstractStateDTO {


  private String creationDate;


  public static StateViewDTOBuilder builder() {
    return new StateViewDTOBuilder();
  }

  public StateViewDTOBuilder construct() {
    return new StateViewDTOBuilder(this);
  }


  public static class StateViewDTOBuilder
      extends AbstractStateBuilder<StateViewDTOBuilder, StateViewHistoryDTO> {

    private StateViewDTOBuilder(StateViewHistoryDTO stateViewHistoryDTO) {
      super("StateViewDTOBuilder", stateViewHistoryDTO);
    }

    private StateViewDTOBuilder() {
      this(new StateViewHistoryDTO());
    }

    public StateViewDTOBuilder creationDate(String creationDate) {
      object.setCreationDate(creationDate);
      return this;
    }

  }

}
