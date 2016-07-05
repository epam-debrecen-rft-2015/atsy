package com.epam.rft.atsy.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO implements Serializable{

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

}
