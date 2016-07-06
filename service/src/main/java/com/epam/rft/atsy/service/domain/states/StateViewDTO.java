package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.states.builder.AbstractStateBuilder;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StateViewDTO extends AbstractStateDTO {


    private String creationDate;


    public static StateViewDTOBuilder builder() {
        return new StateViewDTOBuilder();
    }

    public StateViewDTOBuilder construct() {
        return new StateViewDTOBuilder(this);
    }


    public static class StateViewDTOBuilder extends AbstractStateBuilder<StateViewDTOBuilder, StateViewDTO> {

        private StateViewDTOBuilder(StateViewDTO stateViewDTO) {
            super("StateViewDTOBuilder", stateViewDTO);
        }

        private StateViewDTOBuilder() {
            this(new StateViewDTO());
        }

        public StateViewDTOBuilder creationDate(String creationDate) {
            object.setCreationDate(creationDate);
            return this;
        }

    }

}
