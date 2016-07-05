package com.epam.rft.atsy.service.domain.states;

import lombok.*;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StateViewDTO extends AbstractStateDTO{


    private String creationDate;

}
