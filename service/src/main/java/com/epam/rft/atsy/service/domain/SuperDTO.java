package com.epam.rft.atsy.service.domain;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Super class of every DTO classes. Only declares the {@link #id id} field.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SuperDTO implements Serializable {

  private Long id;
}
