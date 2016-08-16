package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.service.ConverterService;

/**
 * Interface for defining one-way converters to be used in the {@link ConverterService}.
 * If you need to create a new OneWayConverter implementation, you should extend the
 * {@link AbstractOneWayConverter} class, because {@link BaseConverter#generate()} is already
 * implemented in it.
 * @param <F> the first type
 * @param <S> the second type
 */
public interface OneWayConverter<F, S> extends BaseConverter {
  /**
   * Converts the {@code source} with {@code F} type to a {@code S} type object.
   * @param source the object to convert
   * @return the converted object
   */
  S firstTypeToSecondType(F source);

}
