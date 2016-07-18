package com.epam.rft.atsy.service.domain;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder @ToString(exclude = "password") @Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO implements Serializable {

    private Long id;

    @NotNull private String name;

    @NotNull private String password;

}
