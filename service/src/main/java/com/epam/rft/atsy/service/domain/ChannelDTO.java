package com.epam.rft.atsy.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1)
    private String name;

}
