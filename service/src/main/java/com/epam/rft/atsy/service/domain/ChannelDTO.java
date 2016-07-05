package com.epam.rft.atsy.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ChannelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

}
