package com.epam.rft.atsy.service;

import java.util.List;

/**
 * Service for converting DTOs and entities.
 *
 * Usage:
 * <pre>
 * <code>
 * {@literal @}Service
 * public class ExampleService {
 *
 *  {@literal @}Autowired
 *   private ConverterService converterService;
 *
 *   public void methodThatNeedsConverting(ExampleEntity exampleEntity) {
 *     ExampleDTO exampleDto = converterService.convert(exampleEntity, ExampleDTO.class);
 *     // Code working with the exampleDto ....
 *   }
 * }
 *
 * </code>
 * </pre>
 */
public interface ConverterService {
  /**
   * Converts the {@code source} to {@code targetType}.
   * @param source the object to convert
   * @param targetType the type to convert to
   * @param <S> the source type
   * @param <T> the target type
   * @return the converted object with {@code targetType} type
   */
  <S, T> T convert(S source, Class<T> targetType);

  /**
   * Converts the {@code sourceList} to a list of {@code targetType}.
   * @param sourceList the list to convert
   * @param targetType the type to convert to
   * @param <S> the source type
   * @param <T> the target type
   * @return the list of converted objects with {@code targetType} type
   */
  <S, T> List<T> convert(List<S> sourceList, Class<T> targetType);

}
