package com.epam.rft.atsy.service.converter;

import java.util.List;

public interface TwoWayConverter<E, D> {

  D entityToDto(E source);

  E dtoToEntity(D source);

  List<ConverterAdapter> generate();
}
