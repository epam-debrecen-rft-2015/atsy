package com.epam.rft.atsy.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;



@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

}
