package com.epam.rft.atsy.service.domain;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@ToString(exclude = "password")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

  private Long id;

  @NotNull
  private String name;

  @NotNull
  private String password;
}
