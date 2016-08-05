package com.epam.rft.atsy.service.converter;

import org.modelmapper.Converter;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractTwoWayConverter<E, D> implements TwoWayConverter<E, D> {

  @Override
  public List<Converter> generate() {
    return Arrays.asList(
        (Converter<E, D>) mappingContext -> entityToDto(mappingContext.getSource()),
        (Converter<D, E>) mappingContext -> dtoToEntity(mappingContext.getSource())
    );
  }
}
