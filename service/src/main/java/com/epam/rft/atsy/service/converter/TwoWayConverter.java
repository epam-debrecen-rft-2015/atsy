package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.service.ConverterService;

/**
 * Interface for defining two-way converters to be used in the {@link ConverterService}.
 * @param <F> the first type
 * @param <S> the second type
 */
public interface TwoWayConverter<F, S> extends BaseConverter {
  /**
   * Converts the {@code source} with {@code F} type to a {@code S} type object.
   * @param source the object to convert
   * @return the converted object
   */
  S firstTypeToSecondType(F source);

  /**
   * Converts the {@code source} with {@code S} type to a {@code F} type object.
   * @param source the object to convert
   * @return the converted object
   */
  F secondTypeToFirstType(S source);

}
