package com.epam.rft.atsy.service.domain.states;

import com.epam.rft.atsy.service.domain.ChannelDTO;
import lombok.*;

import java.util.Date;




@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StateDTO extends AbstractStateDTO{

    private Date creationDate;
    private ChannelDTO channel;

}
