package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.states.builder.AbstractStateBuilder;

import java.util.Date;
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
public class StateHistoryDTO extends AbstractStateHistoryDTO {

  private Date creationDate;


  public static StateDTOBuilder builder() {
    return new StateDTOBuilder();
  }

  public StateDTOBuilder construct() {
    return new StateDTOBuilder(this);
  }


  public static class StateDTOBuilder extends AbstractStateBuilder<StateDTOBuilder, StateHistoryDTO> {

    private StateDTOBuilder(StateHistoryDTO stateHistoryDTO) {
      super("StateDTOBuilder", stateHistoryDTO);
    }

    private StateDTOBuilder() {
      this(new StateHistoryDTO());
    }

    public StateDTOBuilder creationDate(Date creationDate) {
      object.setCreationDate(creationDate);
      return this;
    }

  }

}
