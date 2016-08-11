package com.epam.rft.atsy.service.converter;

/**
 * Interface for custom converters encapsulated in {@link ConverterAdapter}s.
 * This interface is used internally in the ConverterService.
 * @param <S> the source type
 * @param <T> the target type
 */
public interface CustomConverter<S, T> {

  /**
   * Converts the {@code source} to {@code T} type.
   * @param source the object to convert
   * @return the converted object
   */
  T convert(S source);

}
