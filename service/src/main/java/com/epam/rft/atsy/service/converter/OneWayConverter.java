package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.service.ConverterService;

/**
 * Interface for defining one-way converters to be used in the {@link ConverterService}.
 * @param <S> the source type
 * @param <T> the target type
 */
public interface OneWayConverter<S, T> extends BaseConverter {
  /**
   * Converts the {@code source}.
   * @param source the object to convert
   * @return the converted object
   */
  T sourceTypeToTargetType(S source);

}
