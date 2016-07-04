package com.epam.rft.atsy.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable{

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String password;

}
