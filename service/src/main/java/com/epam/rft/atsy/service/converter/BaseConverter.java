package com.epam.rft.atsy.service.converter;

import java.util.List;

/**
 * Parent interface for {@link OneWayConverter} and {@link TwoWayConverter}.
 * Used for creating custom converter classes, when special business logic needed
 * during converting.
 * @see AbstractOneWayConverter
 * @see AbstractTwoWayConverter
 */
public interface BaseConverter {
  /**
   * Generates a list of {@link ConverterAdapter}s using converting methods defined in child
   * interfaces.
   * @return the list of ConverterAdapters
   */
  List<ConverterAdapter> generate();
}
