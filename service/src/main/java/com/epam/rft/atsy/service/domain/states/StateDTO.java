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
public class StateDTO extends AbstractStateDTO {

  private Date creationDate;


  public static StateDTOBuilder builder() {
    return new StateDTOBuilder();
  }

  public StateDTOBuilder construct() {
    return new StateDTOBuilder(this);
  }


  public static class StateDTOBuilder extends AbstractStateBuilder<StateDTOBuilder, StateDTO> {

    private StateDTOBuilder(StateDTO stateDTO) {
      super("StateDTOBuilder", stateDTO);
    }

    private StateDTOBuilder() {
      this(new StateDTO());
    }

    public StateDTOBuilder creationDate(Date creationDate) {
      object.setCreationDate(creationDate);
      return this;
    }

  }

}
