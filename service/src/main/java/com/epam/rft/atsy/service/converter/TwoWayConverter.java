package com.epam.rft.atsy.service.converter;

import org.modelmapper.Converter;

import java.util.List;

public interface TwoWayConverter<E, D> {

  D entityToDto(E source);

  E dtoToEntity(D source);

  List<Converter> generate();
}
