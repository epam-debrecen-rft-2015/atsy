package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.service.ConverterService;

/**
 * Interface for defining one-way converters to be used in the {@link ConverterService}.
 * If you need to create a new OneWayConverter implementation, you should extend the
 * {@link AbstractOneWayConverter} class, because {@link BaseConverter#generate()} is already
 * implemented in it.
 * @param <S> the source type
 * @param <T> the target type
 */
public interface OneWayConverter<S, T> extends BaseConverter {
  /**
   * Converts the {@code source} with {@code S} type to a {@code T} type object.
   * @param source the object to convert
   * @return the converted object
   */
  T sourceTypeToTargetType(S source);

}
